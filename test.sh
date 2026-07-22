#!/bin/bash

# Define o caminho do arquivo onde o token será salvo
ARQUIVO_TOKEN="$HOME/.token_jwt"

echo "=== SISTEMA DE AUTENTICAÇÃO ==="
echo "1 - CADASTRAR USUÁRIO"
echo "2 - APENAS LOGAR"
read -p "Escolha a operação [1 ou 2]: " OPERACAO

# Validação da Opção 1: Cadastro + Login automático
if [ "$OPERACAO" == "1" ]; then
    echo -e "\n--- CADASTRO DE NOVO USUÁRIO ---"
    read -p "Digite o nome: " NOME
    read -p "Digite o e-mail: " EMAIL
    read -s -p "Digite a senha: " SENHA
    echo "" 
    read -p "Digite o tipo (ex: CLIENTE, ADMIN): " TIPO

    echo -e "\nEnviando requisição de cadastro...\n"
    curl -X POST http://localhost:8080/auth/registrar \
         -H "Content-Type: application/json" \
         -d "{
            \"nome\":\"$NOME\",
            \"email\":\"$EMAIL\",
            \"senha\":\"$SENHA\",
            \"tipo\":\"$TIPO\"
         }"
    
    echo -e "\n\nRealizando login automático..."

# Validação da Opção 2: Apenas Login
elif [ "$OPERACAO" == "2" ]; then
    echo -e "\n--- LOGIN DE USUÁRIO ---"
    read -p "Digite o e-mail: " EMAIL
    read -s -p "Digite a senha: " SENHA
    echo ""

    echo -e "\nRealizando login..."

# Se o usuário digitar qualquer outra coisa
else
    echo "Opção inválida! Encerrando o script."
    exit 1
fi

# Bloco de Login (comum para as opções 1 e 2)
JWT_TOKEN_APP=$(curl -s -X POST http://localhost:8080/auth/login \
     -H "Content-Type: application/json" \
     -d "{
        \"email\":\"$EMAIL\",
        \"senha\":\"$SENHA\"
     }")

# Exibe o token gerado e salva no arquivo físico
echo -e "\n=== RESULTADO ==="
echo "Token JWT gerado: $JWT_TOKEN_APP"

# Salva o token no arquivo para reuso em outros scripts
echo "$JWT_TOKEN_APP" > "$ARQUIVO_TOKEN"
echo "Token salvo com sucesso em: $ARQUIVO_TOKEN"
