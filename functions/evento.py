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
    colnames = ['nome', 'descricao', 'categoria', 'horarioInicio', 'horarioFim', 'regiao', 'endereco', 'enderecoLink', 'organizador']

    query = '''
        SELECT
            ev.nome, ev.descricao, ev.categoria,
            ev.hora_ini, ev.hora_fim,
            ev.regiao, ev.endereco, ev.endereco_link,
            us.nome
        FROM evento AS ev
        JOIN organizador AS og
        ON
            ev.organizador = og.id
        JOIN usuario AS us
        ON
             og.id = us.id
        WHERE
            ev.id = %s
            AND ev.status = 'Aprovado'
        LIMIT 1;
    '''

    params = (id,)

    print(params)

    # executa
    result = dbm.select(conn, query, params, colnames)

    if(result is None or len(result) == 0):
        return 404, "Nenhum evento encontrado"
    
    # pos processamento
    result[0]['organizador'] = { 'nome': result[0]['organizador'] } 

    return 200, result[0]
