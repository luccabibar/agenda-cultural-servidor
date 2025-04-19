from keys.keys_local import Database as kl
from keys.keys_prod import Database as kp

keys = {
    "LOCAL": kl,
    "PROD": kp
}


# SELECAO DE AMBIENTE:
# CONST_ENV = "PROD"
CONST_ENV = "LOCAL"


Database = keys[CONST_ENV]