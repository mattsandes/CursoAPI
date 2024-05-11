#!/bin/bash

set -e

host="$1"
shift
cmd="$@"

until PGPASSWORD=postgres psql -h "5433:5432" -U "postgres" -c '\q'; do
  >&2 echo "Banco de dados está indisponível - aguardando ..."
  sleep 1
done

>&2 echo "Banco de dados está pronto - iniciando a aplicação."
exec $cmd
