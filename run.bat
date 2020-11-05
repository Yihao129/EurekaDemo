cd RegistryServer
start cmd /c run.bat
ping 127.0.0.1 -n 4 > nul
cd ..
cd ApplicationServer
start cmd /c run.bat
cd ..
cd Service1
start cmd /c run.bat
cd ..
cd Service2
start cmd /c run.bat