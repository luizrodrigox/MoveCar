const API_CARROS = "http://localhost:8080/api/carros";
const API_ALUGUEIS = "http://localhost:8080/api/alugueis";

async function carregarCarros() {
	
  const tabela = document.querySelector("#tabela-carros tbody");
  if (!tabela) return;
  tabela.innerHTML = "";
  const res = await fetch(API_CARROS);
  const carros = await res.json();

  carros.forEach(carro => {
    const linha = document.createElement("tr");
    linha.innerHTML = `
      <td>${carro.id}</td>
      <td>${carro.marca}</td>
      <td>${carro.modelo}</td>
      <td>${carro.ano}</td>
      <td>R$ ${carro.diaria.toFixed(2)}</td>
      <td>${carro.disponivel ? "✅ Sim" : "❌ Não"}</td>
      <td>
        <button onclick="abrirModalEdicao(${carro.id})">✏️ Editar</button>
        <button onclick="excluirCarro(${carro.id})">🗑️ Excluir</button>
      </td>
    `;
    tabela.appendChild(linha);
  });
}

async function cadastrarCarro(event) {
  event.preventDefault();
  
  const carro = {
    marca: document.querySelector("#marca").value,
    modelo: document.querySelector("#modelo").value,
    ano: parseInt(document.querySelector("#ano").value),
    diaria: parseFloat(document.querySelector("#diaria").value),
    disponivel: true
  };
  
  await fetch(API_CARROS, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(carro) });
  event.target.reset();
  carregarCarros();
  carregarCarrosDisponiveis();
}

async function excluirCarro(id) {
  await fetch(`${API_CARROS}/${id}`, { method: "DELETE" });
  carregarCarros();
  carregarCarrosDisponiveis();
}

const modal = document.getElementById("modal-edicao");
const spanClose = modal ? modal.querySelector(".close") : null;

function abrirModalEdicao(id) {
  fetch(`${API_CARROS}/${id}`).then(res => res.json()).then(carro => {
    document.getElementById("edit-id").value = carro.id;
    document.getElementById("edit-marca").value = carro.marca;
    document.getElementById("edit-modelo").value = carro.modelo;
    document.getElementById("edit-ano").value = carro.ano;
    document.getElementById("edit-diaria").value = carro.diaria;
	document.getElementById("edit-disponivel").checked = carro.disponivel;
    modal.style.display = "block";
  });
}

if(spanClose) spanClose.onclick = () => modal.style.display = "none";
window.onclick = event => { if(event.target==modal) modal.style.display="none"; }

const formEdicao = document.getElementById("form-edicao");
if(formEdicao) formEdicao.addEventListener("submit", async event => {
  event.preventDefault();
  const id = document.getElementById("edit-id").value;
  
  const carro = {
    marca: document.getElementById("edit-marca").value,
    modelo: document.getElementById("edit-modelo").value,
    ano: parseInt(document.getElementById("edit-ano").value),
    diaria: parseFloat(document.getElementById("edit-diaria").value),
	disponivel: document.getElementById("edit-disponivel").checked
  };
  
  await fetch(`${API_CARROS}/${id}`, { method: "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify(carro) });
  modal.style.display = "none";
  carregarCarros();
  carregarCarrosDisponiveis();
});

async function carregarAlugueis() {
  const tabela = document.querySelector("#tabela-alugueis tbody");
  if(!tabela) return;
  tabela.innerHTML = "";
  const res = await fetch(API_ALUGUEIS);
  const alugueis = await res.json();

  alugueis.forEach(a => {
    const linha = document.createElement("tr");
    linha.innerHTML = `
      <td>${a.id}</td>
      <td>${a.nomeCliente}</td>
      <td>${a.carro ? a.carro.modelo : "-"}</td>
      <td>${a.dataInicio}</td>
      <td>${a.dataFim}</td>
      <td>R$ ${a.valorTotal.toFixed(2)}</td>
      <td>
        <button onclick="abrirModalEdicaoAluguel(${a.id})">✏️ Editar</button>
        <button onclick="excluirAluguel(${a.id})">🗑️ Excluir</button>
      </td>
    `;
    tabela.appendChild(linha);
  });
}

async function carregarCarrosDisponiveis() {
  const select = document.querySelector("#carroId");
  if(!select) return;
  select.innerHTML = "";
  const res = await fetch(`${API_CARROS}/disponiveis`);
  const carros = await res.json();
  carros.forEach(c => select.innerHTML += `<option value="${c.id}">${c.marca} ${c.modelo}</option>`);
}

async function cadastrarAluguel(event) {
  event.preventDefault();
  const aluguel = {
    nomeCliente: document.querySelector("#nomeCliente").value,
    dataInicio: document.querySelector("#dataInicio").value,
    dataFim: document.querySelector("#dataFim").value,
    valorTotal: parseFloat(document.querySelector("#valorTotal").value),
    carro: { id: parseInt(document.querySelector("#carroId").value) }
  };
  
  await fetch(API_ALUGUEIS, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(aluguel) });
  event.target.reset();
  carregarAlugueis();
  carregarCarros();
  carregarCarrosDisponiveis();
}

async function excluirAluguel(id) {
  await fetch(`${API_ALUGUEIS}/${id}`, { method: "DELETE" });
  carregarAlugueis();
  carregarCarros();
  carregarCarrosDisponiveis();
}

const modalAluguel = document.getElementById("modal-edicao-aluguel");
const spanCloseAluguel = modalAluguel ? modalAluguel.querySelector(".close") : null;

function abrirModalEdicaoAluguel(id) {
  fetch(`${API_ALUGUEIS}/${id}`).then(res => res.json()).then(aluguel => {
    document.getElementById("edit-aluguel-id").value = aluguel.id;
    document.getElementById("edit-nomeCliente").value = aluguel.nomeCliente;
    document.getElementById("edit-dataInicio").value = aluguel.dataInicio;
    document.getElementById("edit-dataFim").value = aluguel.dataFim;
    document.getElementById("edit-valorTotal").value = aluguel.valorTotal;

	fetch(API_CARROS).then(r => r.json()).then(carros => {
	  const select = document.getElementById("edit-carroId");
	  select.innerHTML = "";

	  carros.forEach(c => {
		const selected = aluguel.carro && aluguel.carro.id === c.id ? "selected" : "";
	    const disabled = c.disponivel || selected ? "" : "disabled"; // desabilita outros indisponíveis
	    select.innerHTML += `<option value="${c.id}" ${selected} ${disabled}>${c.marca} ${c.modelo}</option>`;
	  });
	});

    modalAluguel.style.display = "block";
  });
}

if(spanCloseAluguel) spanCloseAluguel.onclick = () => modalAluguel.style.display = "none";
window.onclick = event => { if(event.target==modalAluguel) modalAluguel.style.display="none"; }

const formEdicaoAluguel = document.getElementById("form-edicao-aluguel");
if(formEdicaoAluguel) formEdicaoAluguel.addEventListener("submit", async event => {
  event.preventDefault();
  const id = document.getElementById("edit-aluguel-id").value;
  const aluguel = {
    nomeCliente: document.getElementById("edit-nomeCliente").value,
    dataInicio: document.getElementById("edit-dataInicio").value,
    dataFim: document.getElementById("edit-dataFim").value,
    valorTotal: parseFloat(document.getElementById("edit-valorTotal").value),
    carro: { id: parseInt(document.getElementById("edit-carroId").value) }
  };
  
  await fetch(`${API_ALUGUEIS}/${id}`, { method: "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify(aluguel) });
  modalAluguel.style.display = "none";
  carregarAlugueis();
  carregarCarros();
  carregarCarrosDisponiveis();
});

document.addEventListener("DOMContentLoaded", () => {
  carregarCarros();
  carregarAlugueis();
  carregarCarrosDisponiveis();

  const formCarro = document.getElementById("form-carro");
  if(formCarro) formCarro.addEventListener("submit", cadastrarCarro);

  const formAluguel = document.getElementById("form-aluguel");
  if(formAluguel) formAluguel.addEventListener("submit", cadastrarAluguel);
  
  const btnRelatorio = document.getElementById("btn-relatorio");
  if (btnRelatorio) {
      btnRelatorio.addEventListener("click", () => {
      window.open("http://localhost:8080/api/relatorio/pdf", "_blank");
      });
   }
});