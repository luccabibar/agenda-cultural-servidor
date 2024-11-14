import psycopg2 as pg


def connect(host, port, db, user, pw):

    try:
        return pg.connect(
            database=db, 
            host=host, 
            port=port, 
            user=user, 
            password=pw
        )
    
    except: 
        return None


def select(conn, query, values=None, colnames=None):
    
    if(conn is None):
        return None
    
    cur = conn.cursor()

    if(values is None):
        cur.execute(query)
    
    else:
        cur.execute(query, values)


    if(colnames is None or len(colnames) != len(cur.description)):
        result = cur.fetchall()

    else:
        result = []

        for row in cur:
            rr = {}
            
            for ii in range(len(row)):
                rr[colnames[ii]] = row[ii]

            result.append(rr)

    cur.close()

    return result