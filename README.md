# 🚗 MoveCar - Sistema de Gerenciamento de Aluguéis de Carros

## 📋 Tópicos

- [Descrição](#-descrição)
- [Arquitetura do Sistema](#-arquitetura-do-sistema)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Requisitos do Sistema](#-requisitos-do-sistema)
- [Instalação e Execução](#-instalação-e-execução)
- [Banco de Dados](#-banco-de-dados)
- [Geração de Relatórios PDF](#-geração-de-relatórios-pdf)
- [Segurança e Login](#-segurança-e-login)
- [Desenvolvedor](#-desenvolvedor)
- [Licença](#-licença)

---

## 🧩 Descrição

O **MoveCar** é um sistema web completo de **gerenciamento de aluguéis de veículos**, desenvolvido em **Java com Spring Boot**, seguindo o padrão de arquitetura **MVC (Model-View-Controller)**.  

O objetivo do projeto é proporcionar uma aplicação corporativa simplificada, mas funcional, que permita **criar, editar, atualizar e excluir, carros e aluguéis, visualizar relatórios e acessar via login**.

---

## 🏗️ Arquitetura do Sistema

O projeto segue a **arquitetura em três camadas (MVC)**:

1. **Model (Modelo)** – Representa as entidades do sistema (`Car`, `Rental`, `User` etc.).
2. **Controller (Controle)** – Contém as regras de negócio e a ponte entre modelo e visão (`CarController`, `RentalController`, `RelatorioController`).
3. **View (Visão)** – Interface do usuário desenvolvida em **HTML5**, **CSS3** e **JavaScript**, consumindo a API via **requisições REST**.

---

## ⚙️ Funcionalidades

- **Autenticação e autorização de usuários**
  - Login obrigatório para acessar e gerenciar recursos administrativos.
- **Gerenciamento de carros**
  - Cadastro, edição, exclusão e listagem de veículos.
  - Exibição de status de disponibilidade (disponível/alugado).
- **Gerenciamento de aluguéis**
  - Registro de novos aluguéis com controle de datas e valor total.
  - Atualização automática do status dos carros.
- **Relatórios em PDF**
  - Geração dinâmica de relatórios de **carros** e **aluguéis** com layout customizado, via biblioteca **OpenPDF**.
- **Interface amigável**
  - Design simples e intuitivo, com alertas de ação, botões claros e feedback visual.

---

## 💻 Tecnologias Utilizadas

| Camada | Tecnologia |
|--------|-------------|
| **Back-end** | Java 21, Spring Boot, Maven, OpenPDF |
| **Front-end** | HTML5, CSS3, JavaScript |
| **Banco de Dados** | MariaDB |
| **Servidor Web** | Apache Tomcat (embutido no Spring Boot) |
| **IDE Recomendada** | Eclipse 4.0+ |
| **Controle de Versão** | Git e GitHub |

---

## 🧰 Requisitos do Sistema

- **Java JDK 21** ou superior  
- **Apache Maven 3.8+**  
- **MariaDB** instalado e configurado  
- **Eclipse IDE** (com plugin Spring Tools)  
- **Navegador Web moderno** (Chrome, Edge, Firefox)

---

## 🚀 Instalação e Execução

1. **Clone o repositório:**
   
  ```bash
  git clone https://github.com/luizrodrigox/MoveCar.git
  ```

2. **Configuração do banco de dados:**
   
   Execute o arquivo **movecar-db-setup.sql** usando o sistema de gerenciamento de banco de dados.

3. **Acesse a pasta do projeto:**

   Via Terminal:

   ```bash
   cd MoveCar
   ```

4. **Compile e Execute:**

   Via terminal:

   ```bash
   mvn spring-boot:run
   ```

5. **Acesse no navegador:**

   ```bash
   https://localhost:8080/index.html
   ```

6. **Autenticação:**

   - Login: admin
   - Senha: senha123

   No arquivo **SecurityConfig** o login e a senha podem ser alterados através dos campos **.username** e **.password**

## 💾 Banco de Dados

  **Tabelas principais:**

  - car: informações dos veículos.

  - rental: registro de aluguéis e vínculo com carros.

## 📄 Geração de Relatórios PDF

  **Relatórios criados com OpenPDF via endpoint:**

  ```bash
  GET /api/relatorio/pdf
  ```

  **Formato do PDF:**

  - Três carros/aluguéis por linha.
  - Os valores monetários são formatados automaticamente com duas casas decimais.

## 🔐 Segurança e Login

  **O sistema possui controle de acesso:**
    
  - Usuários autenticados podem acessar telas de gerenciamento de carros e aluguéis, com todas as operações de CRUD e gerar relatórios.
  
  - Usuários não autenticados só conseguem gerar relatório.

  Autenticação simples baseada em sessão e validação no front-end com JavaScript.

## 👨‍💻 Desenvolvedor

  **Luiz Rodrigo Melo de Freitas Junior**
  
  - Github: luizrodrigox
    
  - LinkedIn: Luiz Rodrigo

## 📚 Licença

  Este projeto está licenciado sob a licença **MIT**

  Você pode usar, copiar, modificar e distribuir o sistema livremente, desde que mantenha os créditos aos autores originais.
