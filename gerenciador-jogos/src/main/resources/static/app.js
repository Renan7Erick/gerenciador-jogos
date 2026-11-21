const API_JOGOS = '/api/jogos';
const API_PLATAFORMAS = '/api/plataformas';

const telaLista = document.getElementById('tela-lista');
const telaFormJogo = document.getElementById('tela-form-jogo');
const telaFormPlataforma = document.getElementById('tela-form-plataforma');

const listaJogosEl = document.getElementById('lista-jogos');
const mensagemGlobalEl = document.getElementById('mensagem-global');
const erroFormJogoEl = document.getElementById('erro-form-jogo');
const erroFormPlataformaEl = document.getElementById('erro-form-plataforma');

const formJogo = document.getElementById('form-jogo');
const tituloFormJogo = document.getElementById('titulo-form-jogo');
const jogoIdInput = document.getElementById('jogo-id');
const jogoTituloInput = document.getElementById('jogo-titulo');
const jogoAnoInput = document.getElementById('jogo-ano');
const jogoPlataformaSelect = document.getElementById('jogo-plataforma');
const jogoObservacoesInput = document.getElementById('jogo-observacoes');

const formPlataforma = document.getElementById('form-plataforma');
const plataformaNomeInput = document.getElementById('plataforma-nome');

const modalExclusao = document.getElementById('modal-exclusao');
const textoModalExclusao = document.getElementById('texto-modal-exclusao');
const btnConfirmarExclusao = document.getElementById('btn-confirmar-exclusao');
const btnCancelarExclusao = document.getElementById('btn-cancelar-exclusao');

let idParaExcluir = null;

function mostrarTela(tela) {
  telaLista.classList.add('oculto');
  telaFormJogo.classList.add('oculto');
  telaFormPlataforma.classList.add('oculto');
  tela.classList.remove('oculto');
}

function mostrarMensagemGlobal(texto, tipo = 'sucesso') {
  mensagemGlobalEl.textContent = texto;
  mensagemGlobalEl.className = `alerta alerta-${tipo}`;
  mensagemGlobalEl.classList.remove('oculto');
}

function esconderMensagemGlobal() {
  mensagemGlobalEl.classList.add('oculto');
  mensagemGlobalEl.textContent = '';
}

function mostrarErro(elemento, texto) {
  elemento.innerHTML = texto;
  elemento.classList.remove('oculto');
}

function esconderErro(elemento) {
  elemento.classList.add('oculto');
  elemento.innerHTML = '';
}

async function extrairMensagemErro(response) {
  try {
    const data = await response.json();
    if (typeof data === 'string') return data;
    if (data.mensagem) return data.mensagem;
    if (data.message) return data.message;
    if (data.erro) return data.erro;
    if (data.erros && Array.isArray(data.erros)) return data.erros.join('<br>');
    if (typeof data === 'object' && data !== null) {
      const valores = Object.values(data).filter(v => typeof v === 'string');
      if (valores.length > 0) return valores.join('<br>');
    }
  } catch (_) {
  }
  return `Erro ao processar requisição (HTTP ${response.status}).`;
}

async function carregarJogos() {
  try {
    const response = await fetch(API_JOGOS);
    if (!response.ok) {
      throw new Error(await extrairMensagemErro(response));
    }
    const jogos = await response.json();
    renderizarJogos(jogos);
  } catch (error) {
    mostrarMensagemGlobal(error.message || 'Erro ao carregar jogos.', 'erro');
  }
}

function renderizarJogos(jogos) {
  listaJogosEl.innerHTML = '';

  if (!jogos || jogos.length === 0) {
    listaJogosEl.innerHTML = '<div class="vazio">Nenhum jogo cadastrado.</div>';
    return;
  }

  jogos.forEach(jogo => {
    const card = document.createElement('div');
    card.className = 'card-jogo';

    const nomePlataforma = jogo.plataforma ? jogo.plataforma.nome : 'Não informada';
    const badgeIndisponivel = jogo.disponivel === false
      ? '<span class="badge-indisponivel">INDISPONÍVEL</span>'
      : '';

    card.innerHTML = `
            <div class="card-info">
                <div class="card-titulo-linha">
                    <span class="card-titulo">${jogo.titulo}</span>
                    ${badgeIndisponivel}
                </div>
                <div class="card-detalhe">Ano de lançamento: ${jogo.anoLancamento}</div>
                <div class="card-detalhe">Plataforma: ${nomePlataforma}</div>
            </div>
            <div class="card-acoes">
                <button type="button" class="btn btn-azul btn-alterar" data-id="${jogo.id}">Alterar</button>
                <button type="button" class="btn btn-vermelho btn-excluir" data-id="${jogo.id}">Excluir</button>
            </div>
        `;

    card.querySelector('.btn-alterar').addEventListener('click', () => abrirFormAlteracao(jogo.id));
    card.querySelector('.btn-excluir').addEventListener('click', () => abrirModalExclusao(jogo.id));

    listaJogosEl.appendChild(card);
  });
}

async function carregarPlataformas(plataformaSelecionadaId = '') {
  try {
    const response = await fetch(API_PLATAFORMAS);
    if (!response.ok) {
      throw new Error(await extrairMensagemErro(response));
    }
    const plataformas = await response.json();

    jogoPlataformaSelect.innerHTML = '<option value="">Selecione</option>';
    plataformas.forEach(p => {
      const option = document.createElement('option');
      option.value = p.id;
      option.textContent = p.nome;
      if (String(p.id) === String(plataformaSelecionadaId)) {
        option.selected = true;
      }
      jogoPlataformaSelect.appendChild(option);
    });
  } catch (error) {
    mostrarErro(erroFormJogoEl, error.message || 'Erro ao carregar plataformas.');
  }
}

async function abrirFormInclusao() {
  esconderMensagemGlobal();
  esconderErro(erroFormJogoEl);
  formJogo.reset();
  jogoIdInput.value = '';
  tituloFormJogo.textContent = 'Incluir novo jogo';
  document.querySelector('input[name="disponivel"][value="true"]').checked = true;
  await carregarPlataformas();
  mostrarTela(telaFormJogo);
}

async function abrirFormAlteracao(id) {
  esconderMensagemGlobal();
  esconderErro(erroFormJogoEl);
  try {
    const response = await fetch(`${API_JOGOS}/${id}`);
    if (!response.ok) {
      throw new Error(await extrairMensagemErro(response));
    }
    const jogo = await response.json();

    jogoIdInput.value = jogo.id;
    tituloFormJogo.textContent = `Alterar jogo - ID ${jogo.id}`;
    jogoTituloInput.value = jogo.titulo || '';
    jogoAnoInput.value = jogo.anoLancamento || '';
    jogoObservacoesInput.value = jogo.observacoes || '';

    const valorDisponivel = jogo.disponivel === false ? 'false' : 'true';
    document.querySelector(`input[name="disponivel"][value="${valorDisponivel}"]`).checked = true;

    const platId = jogo.plataforma ? jogo.plataforma.id : '';
    await carregarPlataformas(platId);

    mostrarTela(telaFormJogo);
  } catch (error) {
    mostrarMensagemGlobal(error.message || 'Erro ao buscar dados do jogo.', 'erro');
  }
}

formJogo.addEventListener('submit', async (e) => {
  e.preventDefault();
  esconderErro(erroFormJogoEl);

  const id = jogoIdInput.value;
  const disponivelMarcado = document.querySelector('input[name="disponivel"]:checked');
  const plataformaId = jogoPlataformaSelect.value;

  const payload = {
    titulo: jogoTituloInput.value,
    anoLancamento: jogoAnoInput.value ? Number(jogoAnoInput.value) : null,
    plataforma: plataformaId ? { id: Number(plataformaId) } : null,
    disponivel: disponivelMarcado ? disponivelMarcado.value === 'true' : null,
    observacoes: jogoObservacoesInput.value
  };

  const url = id ? `${API_JOGOS}/${id}` : API_JOGOS;
  const method = id ? 'PUT' : 'POST';

  try {
    const response = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      const msgErro = await extrairMensagemErro(response);
      mostrarErro(erroFormJogoEl, msgErro);
      return;
    }

    const msgSucesso = id ? 'Jogo alterado com sucesso!' : 'Jogo incluído com sucesso!';
    mostrarTela(telaLista);
    await carregarJogos();
    mostrarMensagemGlobal(msgSucesso, 'sucesso');
  } catch (error) {
    mostrarErro(erroFormJogoEl, 'Não foi possível conectar ao servidor.');
  }
});

function abrirFormPlataforma() {
  esconderMensagemGlobal();
  esconderErro(erroFormPlataformaEl);
  formPlataforma.reset();
  mostrarTela(telaFormPlataforma);
}

formPlataforma.addEventListener('submit', async (e) => {
  e.preventDefault();
  esconderErro(erroFormPlataformaEl);

  const payload = {
    nome: plataformaNomeInput.value
  };

  try {
    const response = await fetch(API_PLATAFORMAS, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      const msgErro = await extrairMensagemErro(response);
      mostrarErro(erroFormPlataformaEl, msgErro);
      return;
    }

    mostrarTela(telaLista);
    await carregarJogos();
    mostrarMensagemGlobal('Plataforma cadastrada com sucesso!', 'sucesso');
  } catch (error) {
    mostrarErro(erroFormPlataformaEl, 'Não foi possível conectar ao servidor.');
  }
});

function abrirModalExclusao(id) {
  idParaExcluir = id;
  textoModalExclusao.textContent = `Deseja excluir o jogo ID ${id}?`;
  modalExclusao.classList.remove('oculto');
}

function fecharModalExclusao() {
  idParaExcluir = null;
  modalExclusao.classList.add('oculto');
}

btnConfirmarExclusao.addEventListener('click', async () => {
  if (!idParaExcluir) return;
  const id = idParaExcluir;
  fecharModalExclusao();

  try {
    const response = await fetch(`${API_JOGOS}/${id}`, {
      method: 'DELETE'
    });

    if (!response.ok) {
      const msgErro = await extrairMensagemErro(response);
      mostrarMensagemGlobal(msgErro, 'erro');
      return;
    }

    await carregarJogos();
    mostrarMensagemGlobal('Jogo excluído com sucesso!', 'sucesso');
  } catch (error) {
    mostrarMensagemGlobal('Não foi possível conectar ao servidor.', 'erro');
  }
});

btnCancelarExclusao.addEventListener('click', fecharModalExclusao);

document.getElementById('btn-novo-jogo').addEventListener('click', abrirFormInclusao);
document.getElementById('btn-nova-plataforma').addEventListener('click', abrirFormPlataforma);

document.getElementById('btn-cancelar-jogo').addEventListener('click', () => {
  esconderErro(erroFormJogoEl);
  mostrarTela(telaLista);
});

document.getElementById('btn-cancelar-plataforma').addEventListener('click', () => {
  esconderErro(erroFormPlataformaEl);
  mostrarTela(telaLista);
});

carregarJogos();
