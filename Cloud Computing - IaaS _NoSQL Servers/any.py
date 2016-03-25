import bottle
import riak




@bottle.route('/search/:username')
def search(username):
    myClient = riak.RiakClient(host="52.26.163.109",  pb_port=8087, protocol='pbc')
    usersBucket = myClient.bucket('users')
    fetched = usersBucket.get(username)
    val = fetched.data
    print(val)
    if val is not None:
        return val

    else:
        return "User not found :",username

@bottle.route('/add')
@bottle.view('table')
def update():
    return {}

@bottle.route('/add/handle', method='POST')
def submit_form():
    username = bottle.request.forms.get('name')
    number = bottle.request.forms.get('number')

    print(username)
    print(number)

    user = {
        "number": number,
        "name": username
    }

    myClient = riak.RiakClient(host="52.26.163.109", pb_port=8087, protocol='pbc')
    usersBucket = myClient.bucket('users')
    newUser = usersBucket.new(user["name"], data=user)
    fetched = usersBucket.get(username)
    val = fetched.data
    if val is None:
        newUser.store()
        return "Person created: ", username
    else:
        newUser.store()
        return "Number updated : ", username


bottle.run(host='localhost', port=8080)


