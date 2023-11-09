# Java Readers-Writers Application

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Implementation Details](#implementation-details)
- [Contributions](#contributions)
- [License](#license)
- [Contact](#contact)

## Overview
This Java application implements the Readers-Writers problem as part of a Operating Systems class project. The program allows users to select a text file and specify the number of Reader-Writer windows they want to generate.

Each Reader-Writer window has three buttons: one for reading, one for writing, and one for saving modifications. All windows can read the file simultaneously, but only one can write at a time. If a window is reading and another modifies the file, the modifications are refreshed in the reading windows upon saving.

The application uses two semaphores to manage read-write access. The read semaphore has the same number of permits as the number of windows, while the write semaphore has only one permit, allowing only one window to write at a time.

## Features
- Multiple Reader-Writer windows can be generated.
- Simultaneous reading is allowed, but writing is exclusive.
- Refreshing of reading windows upon saving modifications.
- Simple and intuitive user interface.

## Getting Started
1. Clone the repository to your local machine.
   ```bash
   git clone https://github.com/your-username/reader-writer-app.git
   ```
2. Open the project in your preferred Java development environment (e.g., Eclipse, IntelliJ).
3. Run the application and follow the on-screen instructions.

## Usage
1. Launch the application.
2. Select a text file when prompted.
3. Specify the number of Reader-Writer windows to generate.
4. Each window will have buttons for reading, writing, and saving modifications.
5. Click the corresponding buttons to perform the desired actions.

## Implementation Details
- The application utilizes Java's Semaphore class to manage access to shared resources.
- Reader windows acquire and release read permits from the read semaphore.
- Writer windows acquire and release the write permit from the write semaphore.

## Contributions

Contributions are welcome. Feel free to submit issues or pull requests if you have any suggestions or improvements for the project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
If you have any questions or need further assistance, feel free to contact the project maintainer:

Ismael Avila
- Email: ismaelg.avilag@gmail.com
- GitHub: [Ismaelg-Avilag](https://github.com/ismaelg-avilag)
- LinkedIn: [Ismaelg-Avilag](https://www.linkedin.com/in/ismaelg-avilag)
