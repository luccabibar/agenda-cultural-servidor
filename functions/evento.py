from .common import databaseManager as dbm


def evento(dbconf, id):

    # conectar
    conn = dbm.connect(
        dbconf['Host'],
        dbconf['Port'],
        dbconf['Name'],
        dbconf['User'],
        dbconf['Password'],
    )

    if(conn is None):
        return 500, "impossivel conectar com o banco"

    # monta request
    colnames = ['nome', 'descricao', 'categoria', 'horarioInicio', 'horarioFim', 'regiao', 'endereco', 'enderecoLink']

    query = '''
        SELECT
            nome, descricao, categoria,
            hora_ini, hora_fim,
            regiao, endereco, endereco_link
        FROM evento
        WHERE
            id = %s
            AND status = 'Aprovado'
        LIMIT 1;
    '''

    params = (id,)

    print(params)

    # executa
    result = dbm.select(conn, query, params, colnames)

    print(result)
    print(type(result))

    if(result is None or len(result) == 0):
        return 404, "Nenhum evento encontrado"

    else:
        return 200, result[0]
