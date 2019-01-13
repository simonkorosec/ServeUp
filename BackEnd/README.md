# ServeUp-BackEnd

#### Setup Django environment in PyCharm

**Important:** Set up Virtualenv inside project, if Pycharm
doesn't do it by default.

1. Install virtualenv globally using: **pip install virutalenv**
2. https://www.jetbrains.com/help/pycharm/creating-virtual-environment.html

After a successful install, navigate into PyCharm terminal
and run command: **pip install -r requirements.txt**  
which will install all necessary dependencies for the project.

Verify by running the server (SHIFT + F10 or Control + R for Mac)

###### Adding new dependencies

All new dependencies should be added to the *requirements.txt* file, 
otherwise the build on Heroku will fail. This can be achieved by either
adding them manually or using command: **pipreqs [options] \<path>**

Tutorial for pipreqs: https://github.com/bndr/pipreqs



