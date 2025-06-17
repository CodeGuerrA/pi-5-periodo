# 🛠️ Autogyn Oficina — Sistema de Gestão de Ordens de Serviço

Este projeto é um sistema back-end desenvolvido com **Spring Boot** para gerenciar uma oficina mecânica. Ele permite registrar **clientes**, **veículos**, **peças**, **serviços**, **pagamentos** e **ordens de serviço**, além de emitir relatórios em PDF e aplicar **padrões de projeto** para maior organização e extensibilidade do sistema.

## 🚀 Como Usar

### ✅ Requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- IDE como IntelliJ ou VS Code
- Git instalado (opcional)
- Banco de Dados H2 embutido (ou outro via `application.properties`)

### 📦 Passos para executar

1. Clone o repositório:

```bash
git clone https://github.com/CodeGuerrA/pi-5-periodo.git
cd backend
```

2. Compile o projeto:

```bash
./mvnw clean install
```

3. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

4. Acesse no navegador:

```
http://localhost:8080
```

5. Principais Endpoints:

- `/clientes`
- `/veiculos`
- `/pecas`
- `/servicos`
- `/ordens-servico`
- `/pagamentos`
- `/relatorios/financeiro`
## 💡 Padrões de Projeto Utilizados

O projeto utiliza os seguintes padrões de projeto para tornar o código mais modular, reutilizável e extensível:

- **Decorator**
- **Factory**
- **Observer**
- **Iterator**
- **Template Method**

## 📚 Tecnologias Usadas

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Banco H2 (memória) ou configurável
- JasperReports (PDF)
- Maven
- Angular 17

## 📄 Licença

Este projeto é de uso livre para fins acadêmicos e educacionais.
