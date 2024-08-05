# Trabalho final da disciplina de Programação WEB II
João Demetrio e Natan Pivetta


## Serviços

### Verificador de CPF
O serviço de verificação de CPF recebe requisições GET com uma string, realiza os cálculos de dígitos verificadores e retorna um Objeto "Message"
informando se o CPF digitado é válido ou não.

### Serviço de Cadastro
O serviço de Cadastro é um REST Client. Ele solicita um **cpf** e uma **senha** para o usuário, consome o serviço de CPF para validação do mesmo, 
e só após a validação realiza a chamada ao **DB** para a persistência dos dados.

### Serviço de Login
O serviço de Login também é um REST Client. Da mesma forma que o serviço de cadastro, solicita **CPF** e **SENHA**. Após a validação 
do CPF, faz o hash da senha digitada pelo usuário e compara com o hash contido no DB para aquele CPF.

## Algumas recomendações para o uso do DB no Codespaces
##### I. Verificar com "Docker Stats" se o Banco de Dados já está ativo no Container.
##### II. Caso não esteja ativo, rodar **"docker-compose up"** para subir o Banco de Dados (conforme arquivo docker-compose.yml)
##### III. Se for a primeira vez subindo o Container com docker-compose up, talvez ainda não exista um usuário com permissões para acesso ao banco.
##### IV. Abrir console interativo com a aplicação pelo docker com **"docker exec -it <CONTAINER_ID>  mysql -uroot -p"** e criar
novo usuário do Banco com **"CREATE USER 'pw2'@'172.17.0.1' IDENTIFIED BY 'password';"**
Conceder permissões para o usuário com **"GRANT ALL PRIVILEGES ON *.* TO 'pw2'@'172.17.0.1' WITH GRANT OPTION;"**

### Com o banco configurado, os serviços de Login e Cadastro conseguem persistir e acessar os dados no Banco.

