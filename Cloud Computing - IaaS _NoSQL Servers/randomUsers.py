from random import randint
import random
import string
import riak


def generate_users():
    myC = riak.RiakClient(host="52.26.163.109", pb_port=8087, protocol='pbc')
    lowercase = "qwertyuiopasdfghjklzxcvbnm"
    random.seed(10)
    for x in range(0,999):
        user= {
            "number": randint(1000000, 9999999),
            "name":"user"+str(x)
        }
        Bucket = myC.bucket('users')
        User = Bucket.new(user["name"], data=user)
        User.store()
        print(x)
        print(user)
generate_users()
