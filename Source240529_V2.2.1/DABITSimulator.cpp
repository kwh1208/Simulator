//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
//---------------------------------------------------------------------------
USEFORM("TFormMain.cpp", FormMain);
USEFORM("TFormComPort.cpp", FormComPort);
USEFORM("TFormFont.cpp", FormFont);
USEFORM("TFormLEDSet.cpp", FormLEDSet);
USEFORM("TFormFirmware.cpp", FormFirmware);
USEFORM("TFormTransfer.cpp", FormTransfer);
USEFORM("TDataModuleClient.cpp", DataModuleClient); /* TDataModule: File Type */
USEFORM("TFormASCIISet.cpp", FormASCIISet);
USEFORM("TFormMessage.cpp", FormMessage);
USEFORM("TFormSetting.cpp", FormSetting);
USEFORM("TFormLog.cpp", FormLog);
USEFORM("TFormETC.cpp", FormETC);
USEFORM("TFormDABITNet.cpp", FormDABITNet);
USEFORM("TFormDABITOption.cpp", FormDABITOption);
USEFORM("TFormFontName.cpp", FormFontName);
USEFORM("TFormBluetoothSet.cpp", FormBluetoothSet);
//---------------------------------------------------------------------------
WINAPI WinMain(HINSTANCE, HINSTANCE, LPSTR, int)
{
        AnsiString Name = "DABITSimulator 2018.02.14";
        const char *MutexName = Name.c_str();
        HANDLE hInstanceMutex = ::CreateMutex(NULL, true, MutexName);
        /*if (GetLastError() == ERROR_ALREADY_EXISTS)
        {
                if (hInstanceMutex)
                {
                        ReleaseMutex(hInstanceMutex);
                        CloseHandle(hInstanceMutex);
                }
                Application->MessageBox("Now, Program is running!", "Information", MB_OK);
                return 0;
        }*/

        try
        {
                 Application->Initialize();
                 Application->CreateForm(__classid(TFormMain), &FormMain);
                 Application->CreateForm(__classid(TFormLog), &FormLog);
                 Application->CreateForm(__classid(TFormFontName), &FormFontName);
                 Application->CreateForm(__classid(TFormBluetoothSet), &FormBluetoothSet);
                 Application->Run();
        }
        catch (Exception &exception)
        {
                 Application->ShowException(&exception);
        }
        catch (...)
        {
                 try
                 {
                         throw Exception("");
                 }
                 catch (Exception &exception)
                 {
                         Application->ShowException(&exception);
                 }
        }
        if (hInstanceMutex)
        {
                ReleaseMutex(hInstanceMutex);
                CloseHandle(hInstanceMutex);
        }
        return 0;
}
//---------------------------------------------------------------------------
