from .common import databaseManager as dbm


def jaExiste(conn, email, nome):

    # monta request
    colnames = [
        'email', 'nome', 
    ]

    query = '''
        SELECT
            us.email, us.nome 
        FROM usuario AS us
        WHERE
            UPPER(us.email) = UPPER(%s)
            OR UPPER(us.nome) = UPPER(%s)
        ;
    '''

    params = (email, nome)

    # executa
    result = dbm.select(conn, query, params, colnames)

    # retorna de acordo
    if(len(result) > 0):

        res = "Um usuário com estes dados já esta cadastrado"

        for rr in result :
            if(email.upper() == rr['email'].upper()):
                res = "Um usuário com este e-mail já está cadastrado"
                break
            elif(nome.upper() == rr['nome'].upper()):
                res = "Um usuário com este nome já está cadastrado"
                break

        return True, res
    
    else:
        return False, "tudo certo :)"


def criaUsuario(dbconf, email, nome, senha, tipo, cpf = None):

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

    # verifica se usuario ja existe
    jaEx, res = jaExiste(conn, email, nome)

    if(jaEx):
        return 401, res
    
    # TODO: seguir funcao
    else:
        return 200, res


    # segue com cadastro

    # senha deve ser texto + data


    
    
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