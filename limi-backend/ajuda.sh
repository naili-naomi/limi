#!/bin/bash

entrada="$1"
saida="saida.txt"
diretorio_busca="/home/naili/IdeaProjects/limi/"  # Pode mudar para outro diretório de base, se quiser

# Limpa o arquivo de saída
> "$saida"

# Loop pelos nomes no arquivo de entrada
while IFS= read -r nome_arquivo; do
    # Busca o arquivo com nome exato a partir do diretório raiz
    caminho=$(find "$diretorio_busca" -type f -name "$nome_arquivo" 2>/dev/null | head -n 1)

    if [ -n "$caminho" ]; then
        echo "$nome_arquivo (em $caminho):" >> "$saida"
        cat "$caminho" >> "$saida"
        echo -e "\n" >> "$saida"
    else
        echo "$nome_arquivo: [Arquivo não encontrado]" >> "$saida"
        echo >> "$saida"
    fi
done < "$entrada"
