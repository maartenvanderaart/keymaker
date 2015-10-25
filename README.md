# Keymaker, PoC password generator and management

This is a first shot at a webservice for generating passwords and managing
random passwords for local linux users. When started, use curl on the linux
client to obain a parameterised chmod command and pipe it to bash. If you feel
adventurous...

`[maarten@thinkpad ~] curl http://keymakerserver.example.com:4567/updatepassword/root/$(hostname -a)`
returns:
`chmod -p '$6$3RyNKw7O0sdjCvJt$mfUNKD/G5sQVWOwcYIXWeqtr5krn7iiexf88MQqC/pqEryccETYh/9z8mfCaFaFz/YpYueYBnk3IrvPpEiVM3/' root`


Serverside it logs the generated password, the usere it is for and on which
host it was done:
`server 127.0.0.1 requested a password change for user root on thinkpad.example.com`
`{"Username":"root","Hostname":"thinkpad.example.com","Date":"Sun Oct 26 20:47:14 CET 2015","Password":"ocjmc7htc00d55cv7i2bbtkrmc"}`

