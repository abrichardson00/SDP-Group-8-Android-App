# SDP-Group-8-Android-App
## Working with Git
Clone the repository and make sure we're in that directory.

###### andrew@andrew-ThinkPad-T460:~/StudioProjects$ git clone https://github.com/abrichardson00/SDP-Group-8-Android-App.git
###### andrew@andrew-ThinkPad-T460:~/StudioProjects$ cd SDP-Group-8-Android-App

Once some changes have been made this is how one goes about adding it to the repository:

###### git status

^^ check the files it says have changed.

###### git add *

^^ adds all the changed files to be committed.

You can try git status again and it should list the changed files as changes to be committed.



###### git commit

^^ You then commit the changes, this should prompt giving a description of what the commit is.



###### git push
^^ Try uploading your changes.

It might complain that someone else has actually pushed their changes before you. You then try:

###### git pull

Unless theres some weird massive changes that should make your copy up to date with those changes - and then you can just 'git push' again.

If that doesn't work well then it probably means it's time for making a spicy new branch or something idk.
