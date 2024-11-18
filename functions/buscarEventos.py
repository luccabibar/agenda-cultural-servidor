from .common import databaseManager as dbm


def buildWhereClause(texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao):
    
    params = []
    where = ""

    condicoes = [pp for pp in [
        (texto, "UPPER(ev.nome) LIKE CONCAT('%%', UPPER(%s), '%%')"), 
        (categoria, "UPPER(ev.categoria) = UPPER(%s)"), 
        (diaUpper, "CAST('ev.hora_ini' AS DATE) < CAST(%s AS DATE)"), 
        (diaLower, "CAST('ev.hora_ini' AS DATE) > CAST(%s AS DATE)"), 
        (horaUpper, "CAST('ev.hora_ini' AS TIME) < CAST(%s AS TIME)"), 
        (horaLower, "CAST('ev.hora_ini' AS TIME) > CAST(%s AS TIME)"), 
        (regiao, "UPPER(ev.categoria) = UPPER(%s)")
    ] if pp[0] is not None]

    for cc in condicoes:
        if(len(params) == 0):
            where += f'WHERE {cc[1]} '
        else:
            where += f'AND {cc[1]} '

        params.append(cc[0])

    where += ';'

    return where, tuple(params)


def buscarEventos(dbconf, texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao):

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
    colnames = [
        'id', 'nome', 'descricao', 'categoria',
        'horarioInicio', 'regiao',
        'organizador'   
    ]

    query = '''
        SELECT
            ev.id, ev.nome, ev.descricao, ev.categoria,
            ev.hora_ini, ev.regiao,
            us.nome
        FROM evento AS ev
        JOIN organizador AS og
        ON
            ev.organizador = og.id
        JOIN usuario AS us
        ON
            og.id = us.id
    '''

    where, params = buildWhereClause(texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao)

    query += ' ' + where

    # executa
    result = dbm.select(conn, query, params, colnames)

    if(result is None or len(result) == 0):
        return 404, []
    
    # pos processamento
    for rr in result:
        rr['organizador'] = { 'nome': rr['organizador'] } 

    return 200, result


def getEvento():
    pass

def getAtualizacoes():
    pass