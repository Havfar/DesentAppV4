# DesentAppWithGamification

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisities
Which products and technologies you need to install and develop the project.

1. [Android Studio](https://developer.android.com/studio/).
* (Recommended): [NodeJS and NPM](https://nodejs.org/en/).

### Initial setup
1. Open terminal.
* Change directory to where you want the project to be located.
* Clone the repository via the git clone command.
* Open solution, Steffen.sln, in src/ in Visual Studio.

### Building and running the project
1. Restore NuGet packages.
* Set project Steffen.Web as startup project for the solution.
* Rebuild the project (this also installs npm packages as a pre-build event). Wait for it to finish.
  * Warning: May take a long time to build the first time!
* (Recommended): Open terminal.
* (Recommended): Change directory to src/Steffen.Web/ in the project.
* (Recommended): Run npm run watch to retranspile JavaScript on changes in TypeScript files and recompile CSS on changes in LESS files.
* Run the project in Visual Studio to start the application as a IIS Express application.
* A web-browser window should open with the app running in it. You can press the title of the page, "Steffen", to refresh it when new changes are made to the code.

## Known issues
1. When editing HTML files in the project, you sometimes need to clear caches in your web browser to view these changes.
* Changes in project files (adding or deleting files) or any change in the back-end files may cause errors that requires the solution to be rebuilt.
* Pre-build event npm install fails. You need to make sure that Visual Studio is using the newest version of Node and NPM.
