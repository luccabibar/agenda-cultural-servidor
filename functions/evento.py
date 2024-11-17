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
    colnames = [
        'nome', 'descricao', 'categoria', 'contato',
        'horarioInicio', 'horarioFim', 'regiao', 'endereco', 'enderecoLink', 
        'organizador', 
        'attTitulo', 'attTexto'
    ]

    query = '''
        SELECT
            ev.nome, ev.descricao, ev.categoria, ev.contato,
            ev.hora_ini, ev.hora_fim, ev.regiao, ev.endereco, ev.endereco_link,
            us.nome,
            att.titulo, att.texto
        FROM evento AS ev
        JOIN organizador AS og
        ON
            ev.organizador = og.id
        JOIN usuario AS us
        ON
            og.id = us.id
        LEFT JOIN atualizacao_evento AS att
		ON
			ev.id = att.evento
        WHERE
            ev.id = %s
            AND ev.status = 'Aprovado'
        ;
    '''

    params = (id,)

    print(params)

    # executa
    result = dbm.select(conn, query, params, colnames)

    if(result is None or len(result) == 0):
        return 404, "Nenhum evento encontrado"
    
    # pos processamento
    result[0]['atualizacoes'] = []
    
    result[0]['organizador'] = { 'nome': result[0]['organizador'] } 

    if result[0]['attTexto'] is not None:
        
        for row in result:
            result[0]['atualizacoes'].append({
                'titulo': row['attTitulo'],
                'texto': row['attTexto']
            })

        del result[0]['attTitulo']
        del result[0]['attTexto']

    return 200, result[0]
