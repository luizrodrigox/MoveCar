
const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');

const app = fs.readFileSync(
  path.join(__dirname, '..', 'src', 'main', 'resources', 'static', 'app.js'),
  'utf8'
);

const html = fs.readFileSync(
  path.join(__dirname, '..', 'src', 'main', 'resources', 'static', 'index.html'),
  'utf8'
);

test('define os endpoints de carros e aluguéis', () => {
  assert.match(app, /API_CARROS\s*=\s*["']\/api\/carros["']/);
  assert.match(app, /API_ALUGUEIS\s*=\s*["']\/api\/alugueis["']/);
});

test('possui funções de gerenciamento de carros', () => {
  assert.match(app, /function carregarCarros\s*\(/);
  assert.match(app, /function cadastrarCarro\s*\(/);
  assert.match(app, /function excluirCarro\s*\(/);
  assert.match(app, /function abrirModalEdicao\s*\(/);
});

test('possui funções de gerenciamento de aluguéis', () => {
  assert.match(app, /function carregarAlugueis\s*\(/);
  assert.match(app, /function cadastrarAluguel\s*\(/);
  assert.match(app, /function excluirAluguel\s*\(/);
  assert.match(app, /function abrirModalEdicaoAluguel\s*\(/);
});

test('utiliza métodos HTTP para criar, editar e excluir', () => {
  assert.match(app, /method:\s*["']POST["']/);
  assert.match(app, /method:\s*["']PUT["']/);
  assert.match(app, /method:\s*["']DELETE["']/);
});

test('registra eventos após o carregamento da página', () => {
  assert.match(app, /DOMContentLoaded/);
  assert.match(app, /form-carro/);
  assert.match(app, /form-aluguel/);
});

test('contém links para carros e aluguéis', () => {
  assert.match(html, /href=["']carros\.html["']/);
  assert.match(html, /href=["']alugueis\.html["']/);
});

test('inclui o botão de relatório e o script do frontend', () => {
  assert.match(html, /id=["']btn-relatorio["']/);
  assert.match(html, /src=["']app\.js["']/);
});
