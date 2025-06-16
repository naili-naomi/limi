#!/bin/bash

lista="arquivos1.txt"
saida="saida.txt"
raiz="/home/naili/IdeaProjects/limi/"  # Pasta onde buscar os arquivos

> "$saida"  # Limpa o conteúdo do arquivo de saída, se existir

while IFS= read -r nome_arquivo; do
    [ -z "$nome_arquivo" ] && continue  # pula linhas em branco

    # Busca o primeiro arquivo com esse nome dentro de centra/
    caminho_encontrado=$(find "$raiz" -type f -name "$nome_arquivo" | head -n 1)

    if [ -n "$caminho_encontrado" ]; then
        echo "$nome_arquivo:" >> "$saida"
        echo '"' >> "$saida"
        cat "$caminho_encontrado" >> "$saida"
        echo '"' >> "$saida"
        echo >> "$saida"
    else
        echo "$nome_arquivo:" >> "$saida"
        echo "\"[Arquivo não encontrado em $raiz/]\"" >> "$saida"
        echo >> "$saida"
    fi
done < "$lista"

