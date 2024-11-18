from .common import databaseManager as dbm


def getBuscarParams(dbconf):

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


    # requests
    regioes = getRegioes(conn)
    categorias = getCategorias(conn)

    # pos processamento
    result = {
        'regioes': [rr['regioes'] for rr in regioes],
        'categorias': [cc['categorias'] for cc in categorias]
    }

    return 200, result


def getRegioes(conn):

    # monta request
    colnames = ['regioes']

    query = '''
        SELECT
            rg.regiao
        FROM regiao_evento AS rg
        ;
    '''

    params = ()

    # executa
    result = dbm.select(conn, query, params, colnames)

    if(result is None or len(result) == 0):
        return []
    else:
        return result


def getCategorias(conn):

    # monta request
    colnames = ['categorias']

    query = '''
        SELECT
            ct.categoria
        FROM categoria_evento AS ct
        ;
    '''

    params = ()

    # executa
    result = dbm.select(conn, query, params, colnames)

    if(result is None or len(result) == 0):
        return []
    else:
        return result