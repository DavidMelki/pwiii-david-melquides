### 1. **Crie um Projeto em Laravel**

Laravel é um framework PHP usado para facilitar o desenvolvimento de aplicações web de forma organizada e eficiente, seguindo o padrão MVC (Model-View-Controller).

---

### 💠 Pré-requisitos

Antes de começar, você precisa ter algumas ferramentas instaladas no seu computador:

* **PHP 8.1+**: É a linguagem em que o Laravel é escrito. A versão mínima recomendada é 8.1.
* **Composer**: Gerenciador de dependências do PHP, ele instala as bibliotecas necessárias para o Laravel funcionar.
* **Node.js + npm**: Usado para gerenciar pacotes de frontend e compilar assets (CSS, JavaScript) via Vite.
* **Banco de dados**: Pode ser MySQL, PostgreSQL ou SQLite. Laravel conecta sua aplicação ao banco para armazenar dados.
* **Laravel Installer** (opcional): Uma ferramenta para criar projetos Laravel rapidamente via terminal.

---

### 📚 Passo a Passo

#### 🛠 **Criar a aplicação Laravel**

```bash
laravel new example-app
```

* Este comando cria uma nova pasta chamada `example-app` com todo o esqueleto do projeto Laravel configurado.
* Ele baixa todos os arquivos necessários, bibliotecas e configurações básicas.
* O instalador pode perguntar algumas configurações, como o banco que você vai usar, framework de testes e kits iniciais, que são conjuntos prontos para ajudar no começo do projeto.

---

#### 📂 **Acesse a pasta do projeto**

```bash
cd example-app
```

* Agora você entra na pasta do projeto para executar comandos dentro dela.

---

#### 📦 **Instale as dependências frontend e compile os assets**

```bash
npm install && npm run build
```

* `npm install` instala as bibliotecas JavaScript necessárias para seu frontend (CSS, JS, etc).
* `npm run build` compila esses arquivos para produção, transformando código moderno em algo que todos os navegadores entendem.

---

#### ⚙️ **Inicie o ambiente completo de desenvolvimento**

```bash
composer run dev
```

* Este comando inicia vários serviços que você vai precisar enquanto desenvolve:

  * **Servidor local Laravel** (`php artisan serve`): roda a aplicação no endereço `http://localhost:8000`.
  * **Queue worker**: processa tarefas em segundo plano (exemplo: envio de e-mails).
  * **Servidor Vite**: atualiza automaticamente seu frontend quando você salva mudanças nos arquivos CSS ou JS.

---

#### 🔧 **Configuração do ambiente**

```bash
cp .env.example .env
php artisan key:generate
```

* `.env` é o arquivo de configuração do seu projeto (dados sensíveis, configurações de banco, etc).
* Você copia o arquivo de exemplo para `.env` para começar a personalizar.
* `php artisan key:generate` cria uma chave única para sua aplicação, usada para proteger sessões e dados criptografados.

---

#### 🗄 **Configure seu banco de dados**

Edite o arquivo `.env` para informar os dados do seu banco, por exemplo:

```
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=exemplo
DB_USERNAME=root
DB_PASSWORD=secret
```

* Essas informações dizem ao Laravel onde e como se conectar ao banco de dados.

---

#### 🧱 **Execute as migrations (opcional)**

```bash
php artisan migrate
```

* Migrations são como "controladores de versão" para o banco de dados.
* Esse comando cria as tabelas no banco que Laravel precisa para funcionar (exemplo: tabela de usuários).

---

#### 🧪 **Teste no navegador**

* Acesse: `http://localhost:8000`
* Você verá a página inicial padrão do Laravel, que confirma que seu projeto está funcionando.

---

#### Para criar um controller (exemplo simples)

```bash
php artisan make:controller HelloController
```

* Esse comando gera uma classe Controller, onde você vai colocar a lógica para responder às requisições web.

---

### 🌐 Links úteis

* [Documentação oficial do Laravel](https://laravel.com/docs)
* [Laracasts](https://laracasts.com/) — plataforma de vídeo-aulas focada em Laravel
* [Documentação do PHP](https://www.php.net/manual/pt_BR/)

---

### ✅ Resumo

Você instalou tudo que precisa, criou o projeto, configurou o banco, compilou o frontend e colocou a aplicação para rodar no navegador. A partir daqui, é só começar a desenvolver suas funcionalidades!
