[![Build Status](https://travis-ci.org/maartenvanderaart/keymaker.svg?branch=travis-integration)](https://travis-ci.org/maartenvanderaart/keymaker)

# Keymaker, PoC password generator and management

This is a first shot at a webservice for generating passwords and managing
random passwords for local linux users. When started, use curl on the linux
client to obtain a parametrised usermod command and pipe it to bash. If you feel
adventurous...

`[maarten@thinkpad ~] curl http://keymakerserver.example.com:4567/updatepassword/root/$(hostname -f)`
returns:
`usermod -p '$6$3RyNKw7O0sdjCvJt$mfUNKD/G5sQVWOwcYIXWeqtr5krn7iiexf88MQqC/pqEryccETYh/9z8mfCaFaFz/YpYueYBnk3IrvPpEiVM3/' root`


Serverside it writes the generated password, the user it is for and on which
host it was done to a file with the name of $user@$hostname.txt in a directory
(outputDir) that is currently not configurable.
Console output:
`[INFO ] 2015-11-06 18:02:07.633 [qtp602412982-23] Main - server 127.0.0.1 requested a password change for user root on thinkpad`
`[INFO ] 2015-11-06 18:02:07.633 [qtp602412982-23] Main - wrote new password to outputDir/root@thinkpad.txt`

The written file:
`[maarten@thinkpad ~]$ cat outputDir/root@thinkpad.txt`
`Date = Fri Nov 06 18:02:07 CET 2015, Hostname = thinkpad, Username = root, Password = 4go7em048v4t3a228trmud8ekb`

This file is appended, every time a new password for this user/host combination
is requested. This is so that there's a history of generated passwords, in case
at some point updating the password fails at the client side and the last password
the server knows is not the last password that was set for the user.

To run successfully please make sure that when
you start the service, a directory called 'ouputDir' is present in the working directory
and that it is writable. The server will return a 500 error if this directory is not
present.

Please note that this is a Proof of Concept, hence PoC in the title. I claim no fitness
for any real world use at this stage. I am open to suggestions, but don't expect miracles ;-)