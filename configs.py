from keys import keys_local as kl
from keys import keys_prod as kp

keys = {
    "LOCAL": kl,
    "PROD": kp
}


# SELECAO DE AMBIENTE:
# CONST_ENV = "PROD"
CONST_ENV = "LOCAL"


Database = keys[CONST_ENV]