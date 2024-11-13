from hashlib import sha256

def hashPassword(pw, salt):

    st = pw + salt
    return sha256(st.encode('utf-8')).hexdigest()
