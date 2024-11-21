# MS Order - Microserviço para Gestão de Pedidos

## Sobre o Projeto

Este é um microserviço desenvolvido para gestão de pedidos, utilizando o **Spring Framework**. Ele faz integração com **RabbitMQ** para comunicação assíncrona, **JPA** para persistência de dados e **Swagger** para documentação da API. O sistema é modular e fácil de testar, com endpoints bem definidos e documentação disponível via Swagger. E conta com cobertura de testes unitários com Mockito.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para a construção da aplicação.
- **Spring Data JPA**: Para persistência de dados no banco de dados.
- **Spring AMQP**: Para integração com RabbitMQ.
- **Swagger**: Para documentação da API.
- **JUnit & Mockito**: Para testes unitários.
- **RabbitMQ**: Para comunicação assíncrona entre os serviços.
- **Java 17**: Versão do JDK utilizada.

## Como Utilizar a API

### Endpoints Disponíveis

A aplicação oferece endpoints para gerenciar os pedidos. A documentação completa dos endpoints pode ser acessada via Swagger após rodar a aplicação, no seguinte link:

[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

### **Endpoints de Pedido (OrderController)**

#### 1. **Listagem de Pedidos (Paginação)**

- **Método**: `GET`
- **Endpoint**: `/orders`
- **Descrição**: Retorna uma lista de pedidos paginada.
- **Parâmetros**:
    - `page` (opcional): Página para a busca paginada.
    - `size` (opcional): Quantidade de itens por página.

**Exemplo de requisição**:

```http
GET http://localhost:8080/orders?page=0&size=10
```

#### 2. **Consultar Pedido por ID**

- **Método**: `GET`
- **Endpoint**: `/orders/{id}`
- **Descrição**: Retorna um pedido específico pelo ID.

**Exemplo de requisição**:

```http
GET http://localhost:8080/orders/1
```

### **Processamento de Pedidos (OrderListener)**

A aplicação também possui integração com **RabbitMQ**, onde a fila de pedidos é ouvida pelo serviço para processar os pedidos de forma assíncrona.

- **Fila**: `orders.details-requests`
- **Descrição**: Quando um pedido é colocado na fila `orders.details-requests`, ele será processado pelo `OrderListener`, que irá criar ou atualizar o pedido com base no ID do pedido.

### Payload para `OrderDTO`

Aqui está um exemplo de payload para um objeto `OrderDTO` que pode ser enviado para a fila `orders.details-requests`:

```json
{
  "ordersId": 324,
  "customerId": 321,
  "dateTime": "2024-11-12T16:45:00",
  "status": "PLACED",
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "description": "Product A",
      "price": 10.99
    },
    {
      "productId": 2,
      "quantity": 1,
      "description": "Product B",
      "price": 25.50
    }
  ]
}
```

### Campos do `OrderDTO`:

- `ordersId` (Long): Identificador único do pedido.
- `customerId` (Long): ID do cliente que fez o pedido.
- `dateTime` (String - ISO 8601): Data e hora em que o pedido foi feito.
- `status` (String): Status atual do pedido. Exemplos de valores:
    - `PLACED`: Pedido foi realizado com sucesso, mas ainda não processado.
    - `CANCELED`: Pedido foi cancelado.
    - `CONFIRMED`: Pedido foi confirmado para processamento.
    - `READY`: Pedido está pronto para ser enviado.
    - `OUT_FOR_DELIVERY`: Pedido está em processo de entrega.
    - `DELIVERED`: Pedido foi entregue ao cliente.
- `totalAmount` (BigDecimal): Valor total do pedido, calculado com base no preço, quantidade dos itens e eventual desconto.
- `items` (Array): Lista de itens do pedido. Cada item deve conter:
    - `productId` (Long): Identificador do item.
    - `quantity` (Integer): Quantidade do item.
    - `description` (String): Descrição do produto.
    - `price` (BigDecimal): Preço unitário de Itens do produto.

## Como Rodar a Aplicação

### Rodando a Aplicação com Docker

#### Passo 1: Instalando o Docker

1. **Instalar o Docker**:
    - Para **Windows** e **Mac**: Baixe e instale o Docker Desktop [aqui](https://www.docker.com/products/docker-desktop).
    - Para **Linux**: Siga as instruções específicas para a sua distribuição [aqui](https://docs.docker.com/engine/install/).

2. **Verificar a instalação do Docker**:
   Após instalar, verifique se o Docker está instalado corretamente executando o comando:
   ```bash
   docker --version
   ```

#### Passo 2: Rodando o RabbitMQ com Docker

Para rodar o RabbitMQ com Docker, voc�� pode usar o seguinte comando:
```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

#### Passo 3: Rodando a Aplicação com Docker Compose

1. **Subir os serviços com Docker Compose**:
   ```bash
   docker-compose up --build
   ```

2. **Acessar a aplicação**:
    - A aplicação estará disponível em `http://localhost:8080`.
    - O RabbitMQ estará disponível em `http://localhost:15672` (usuário: `guest`, senha: `guest`).

### Rodando a Aplicação Localmente

#### Ambiente de Desenvolvimento (dev)

1. **Clone o repositório para sua máquina local**:
   ```bash
   git clone https://github.com/seu-usuario/ms-order.git
   cd ms-order
   ```

2. **Compile o projeto com Maven**:
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação com o perfil de desenvolvimento**:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

4. **Acesse a documentação Swagger**:
   Acesse os endpoints da aplicação através da documentação Swagger no seguinte link:
   [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

#### Ambiente de Teste (test)

1. **Clone o repositório para sua máquina local**:
   ```bash
   git clone https://github.com/seu-usuario/ms-order.git
   cd ms-order
   ```

2. **Compile o projeto com Maven**:
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação com o perfil de teste**:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=test
   ```

4. **Acesse a documentação Swagger**:
   Acesse os endpoints da aplicação através da documentação Swagger no seguinte link:
   [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

### Alterando o Perfil Ativo

Para alternar entre os perfis de desenvolvimento e teste, você pode modificar a linha `spring.profiles.active` no arquivo `application.properties`:

- Para o ambiente de desenvolvimento, defina:
  ```ini
  spring.profiles.active=dev
  ```

- Para o ambiente de teste, defina:
  ```ini
  spring.profiles.active=test
  ```

## Escalando a Aplicação com Kubernetes

Para lidar com milhares de requisições, você pode usar Kubernetes para escalar a aplicação. Aqui estão os passos básicos para configurar e escalar a aplicação com Kubernetes:

### Passo 1: Criar os Arquivos de Configuração do Kubernetes

Crie um arquivo `deployment.yaml` para definir o deployment da aplicação:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-order
spec:
  replicas: 3
  selector:
    matchLabels:
      app: ms-order
  template:
    metadata:
      labels:
        app: ms-order
    spec:
      containers:
      - name: ms-order
        image: seu-usuario/ms-order:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "dev"
```

Crie um arquivo `service.yaml` para definir o serviço que expõe a aplicação:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: ms-order-service
spec:
  selector:
    app: ms-order
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
  type: LoadBalancer
```

### Passo 2: Aplicar as Configurações no Kubernetes

1. **Iniciar o Minikube (ou outro cluster Kubernetes)**:
   ```bash
   minikube start
   ```

2. **Aplicar os arquivos de configuração**:
   ```bash
   kubectl apply -f deployment.yaml
   kubectl apply -f service.yaml
   ```

3. **Verificar os pods e serviços**:
   ```bash
   kubectl get pods
   kubectl get services
   ```

### Passo 3: Acessar a Aplicação

Após aplicar as configurações, o Kubernetes criará um LoadBalancer que expõe a aplicação. Você pode acessar a aplicação através do IP externo fornecido pelo serviço LoadBalancer.

### Escalando a Aplicação

Para escalar a aplicação, você pode ajustar o número de réplicas no arquivo `deployment.yaml` ou usar o comando `kubectl scale`:

```bash
kubectl scale deployment ms-order --replicas=5
```

Isso aumentará o número de instâncias da aplicação para 5, permitindo que ela lide com um maior número de requisições (solução para dar conta da previsão de milhares de requisições do desafio).