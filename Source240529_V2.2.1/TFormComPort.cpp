//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormComPort.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntStdCtrls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormComPort *FormComPort;
//---------------------------------------------------------------------------
__fastcall TFormComPort::TFormComPort(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption = FormMain->LangFile.FormComportCaption;
        pnCaption->Caption = " " + this->Caption;
        if(FormMain->ComKind == 0)
        {
            BPortClose->Caption = FormMain->LangFile.FormComportButton1;
            BPortCon->Caption = FormMain->LangFile.FormComportButton2;
        }
        else
        {
            BPortClose->Caption = FormMain->LangFile.FormComportButton4;
            BPortCon->Caption = FormMain->LangFile.FormComportButton5;
        }
        rbSerial->Caption = FormMain->LangFile.FormComportrbSerial;
        laRS485->Caption = FormMain->LangFile.FormComportAddress;
        BSearch->Caption = FormMain->LangFile.FormComportSearch;
        BSystem->Caption = FormMain->LangFile.FormComportSystem;
        Label1->Caption = FormMain->LangFile.FormComportLabel1;
        TeLabel1->Caption = FormMain->LangFile.FormComportLabel2;
        rbLAN->Caption = FormMain->LangFile.FormComportrbLAN;
        rbServer->Caption = FormMain->LangFile.FormComportrbServer;
        rbUDP->Caption = FormMain->LangFile.FormComportrbUDP;
        laRTime->Caption = FormMain->LangFile.FormComportRTime;
        BConnect->Caption = FormMain->LangFile.FormComportConnect;
        BitBtnOk->Caption = FormMain->LangFile.MessageButton10;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        rbSerial->Color = FormMain->MainColor;
        gbSerial->Color = FormMain->MainRectangleColor;
        pnSerial->Color = FormMain->MainColor;
        gbLAN->Color = FormMain->MainRectangleColor;
        rbLAN->Color = FormMain->MainColor;
        pnLAN->Color = FormMain->MainColor;
        gbServer->Color = FormMain->MainRectangleColor;
        rbServer->Color = FormMain->MainColor;
        pnServer->Color = FormMain->MainColor;
        gbUDP->Color = FormMain->MainRectangleColor;
        rbUDP->Color = FormMain->MainColor;
        pnUDP->Color = FormMain->MainColor;
        rbSerial->Font->Color = FormMain->TextColor;
        rbSerial->Font->Name = FormMain->MainFont;
        rbSerial->Font->Size = FormMain->MainFontSize;
        Label1->Font->Color = FormMain->TextColor;
        Label1->Font->Name = FormMain->MainFont;
        Label1->Font->Size = FormMain->MainFontSize;
        ComBox1->Font->Name = FormMain->MainFont;
        ComBox1->Font->Size = FormMain->MainFontSize;
        TeLabel1->Font->Color = FormMain->TextColor;
        TeLabel1->Font->Name = FormMain->MainFont;
        TeLabel1->Font->Size = FormMain->MainFontSize;
        BaudBox1->Font->Name = FormMain->MainFont;
        BaudBox1->Font->Size = FormMain->MainFontSize;
        cbRS485->Font->Name = FormMain->MainFont;
        cbRS485->Font->Size = FormMain->MainFontSize;
        cbDIBDAddress->Font->Name = FormMain->MainFont;
        cbDIBDAddress->Font->Size = FormMain->MainFontSize;
        laRS485->Font->Color = FormMain->TextColor;
        laRS485->Font->Name = FormMain->MainFont;
        laRS485->Font->Size = FormMain->MainFontSize;
        rbLAN->Font->Color = FormMain->TextColor;
        rbLAN->Font->Name = FormMain->MainFont;
        rbLAN->Font->Size = FormMain->MainFontSize;
        TntLabel4->Font->Color = FormMain->TextColor;
        TntLabel4->Font->Name = FormMain->MainFont;
        TntLabel4->Font->Size = FormMain->MainFontSize;
        Edit2->Font->Name = FormMain->MainFont;
        Edit2->Font->Size = FormMain->MainFontSize;
        Label3->Font->Color = FormMain->TextColor;
        Label3->Font->Name = FormMain->MainFont;
        Label3->Font->Size = FormMain->MainFontSize;
        Edit3->Font->Name = FormMain->MainFont;
        Edit3->Font->Size = FormMain->MainFontSize;
        rbServer->Font->Color = FormMain->TextColor;
        rbServer->Font->Name = FormMain->MainFont;
        rbServer->Font->Size = FormMain->MainFontSize;
        TntLabel5->Font->Color = FormMain->TextColor;
        TntLabel5->Font->Name = FormMain->MainFont;
        TntLabel5->Font->Size = FormMain->MainFontSize;
        Edit6->Font->Name = FormMain->MainFont;
        Edit6->Font->Size = FormMain->MainFontSize;
        TntLabel6->Font->Color = FormMain->TextColor;
        TntLabel6->Font->Name = FormMain->MainFont;
        TntLabel6->Font->Size = FormMain->MainFontSize;
        Edit4->Font->Name = FormMain->MainFont;
        Edit4->Font->Size = FormMain->MainFontSize;
        TntLabel3->Font->Color = FormMain->TextColor;
        TntLabel3->Font->Name = FormMain->MainFont;
        TntLabel3->Font->Size = FormMain->MainFontSize;
        Edit5->Font->Name = FormMain->MainFont;
        Edit5->Font->Size = FormMain->MainFontSize;
        rbUDP->Font->Color = FormMain->TextColor;
        rbUDP->Font->Name = FormMain->MainFont;
        rbUDP->Font->Size = FormMain->MainFontSize;
        TntLabel1->Font->Color = FormMain->TextColor;
        TntLabel1->Font->Name = FormMain->MainFont;
        TntLabel1->Font->Size = FormMain->MainFontSize;
        TntLabel2->Font->Color = FormMain->TextColor;
        TntLabel2->Font->Name = FormMain->MainFont;
        TntLabel2->Font->Size = FormMain->MainFontSize;
        BSearch->Font->Color = FormMain->TextBGColor;
        BSearch->Font->Name = FormMain->MainFont;
        BSearch->Font->Size = FormMain->MainFontSize;
        pnBSearch->Color = FormMain->ButtonColor;
        BSystem->Font->Color = FormMain->TextBGColor;
        BSystem->Font->Name = FormMain->MainFont;
        BSystem->Font->Size = FormMain->MainFontSize;
        pnBSystem->Color = FormMain->ButtonColor;
        BLEDSoftNet->Font->Color = FormMain->TextBGColor;
        BLEDSoftNet->Font->Name = FormMain->MainFont;
        BLEDSoftNet->Font->Size = FormMain->MainFontSize;
        pnBLEDSoftNet->Color = FormMain->ButtonColor;
        BBluetooth->Font->Color = FormMain->TextBGColor;
        BBluetooth->Font->Name = FormMain->MainFont;
        BBluetooth->Font->Size = FormMain->MainFontSize;
        pnBBluetooth->Color = FormMain->ButtonColor;
        BPortCon->Font->Color = FormMain->TextBGColor;
        BPortCon->Font->Name = FormMain->MainFont;
        BPortCon->Font->Size = FormMain->MainFontSize;
        pnBPortCon->Color = FormMain->ButtonColor;
        BPortClose->Font->Color = FormMain->TextBGColor;
        BPortClose->Font->Name = FormMain->MainFont;
        BPortClose->Font->Size = FormMain->MainFontSize;
        pnBPortClose->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
        BConnect->Font->Color = FormMain->TextBGColor;
        BConnect->Font->Name = FormMain->MainFont;
        BConnect->Font->Size = FormMain->MainFontSize;
        pnBConnect->Color = FormMain->ButtonColor;
        laRTime->Font->Color = FormMain->TextColor;
        laRTime->Font->Name = FormMain->MainFont;
        laRTime->Font->Size = FormMain->MainFontSize;
        TntLabel9->Font->Color = FormMain->TextColor;
        TntLabel9->Font->Name = FormMain->MainFont;
        TntLabel9->Font->Size = FormMain->MainFontSize;
        cbWaitTime->Font->Name = FormMain->MainFont;
        cbWaitTime->Font->Size = FormMain->MainFontSize;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::FormCreate(TObject *Sender)
{
        SearchComPort();
        ComBox1->ItemIndex = ComBox1->Items->IndexOf(FormMain->PortUse1);
        if(ComBox1->ItemIndex < 0)
            ComBox1->Text = FormMain->PortUse1;
        BaudBox1->ItemIndex = FormMain->BaudRateUse1;
        cbRS485->Checked = FormMain->bRS485;
        Edit2->Text = FormMain->IPAddress;
        Edit3->Text = IntToStr(FormMain->IPPort);
        Edit4->Text = FormMain->UDPIPAddress;
        Edit5->Text = IntToStr(FormMain->DSPPort);
        Edit6->Text = IntToStr(FormMain->ServerPort);
        try{
            TntLabel6->Caption = FormMain->GetLocalIP();
        }catch(...){
        }
        if(FormMain->ComKind == 1)
            rbLAN->Checked = true;
        else if(FormMain->ComKind == 2)
            rbServer->Checked = true;
        else if(FormMain->ComKind == 3)
            rbUDP->Checked = true;
        else
            rbSerial->Checked = true;
        int tempDelay = 5;
        if(FormMain->ComKind == 0)
            tempDelay = 3;
        else if(FormMain->ComKind == 2)
            tempDelay = 10;
        cbWaitTime->Clear();
        for(int i = 1; i <= tempDelay; i++)
        {
            cbWaitTime->Items->Add(IntToStr(i));
        }
        cbWaitTime->ItemIndex = (FormMain->WaitTime / 1000) - 1;
        if(cbWaitTime->ItemIndex < 0)
            cbWaitTime->ItemIndex = 0;
        int tempIndex = FormMain->Controler1;
        cbDIBDAddress->Clear();
        //char Buf[3];
        for(int i = 0; i < FormMain->cbDIBDAddress->Items->Count; i++)
        {
            //ZeroMemory(Buf, 3);
            //wsprintf(Buf, "%02d", i);
            cbDIBDAddress->Items->Add(FormMain->cbDIBDAddress->Items->Strings[i]);
        }
        FormMain->Controler1 = tempIndex;
        cbDIBDAddress->ItemIndex = FormMain->Controler1;
        cbDIBDAddress->Visible = cbRS485->Checked;
        //BLEDSoftNet->Visible = FormMain->ComKind;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::rbComKind(TObject *Sender)
{
        if(BPortClose->Enabled)
            BPortClose->Click();
        Label1->Enabled = rbSerial->Checked;
        ComBox1->Enabled = rbSerial->Checked;
        TeLabel1->Enabled = rbSerial->Checked;
        BaudBox1->Enabled = rbSerial->Checked;
        laRS485->Enabled = rbSerial->Checked;
        cbRS485->Enabled = rbSerial->Checked;
        cbDIBDAddress->Enabled = rbSerial->Checked;
        BSystem->Enabled = rbSerial->Checked;
        BSearch->Enabled = rbSerial->Checked;
        //BLEDSoftNet->Enabled = !rbSerial->Checked;
        TntLabel4->Enabled = rbLAN->Checked;
        Edit2->Enabled = rbLAN->Checked;
        Label3->Enabled = rbLAN->Checked;
        Edit3->Enabled = rbLAN->Checked;
        TntLabel1->Enabled = rbUDP->Checked;
        Edit4->Enabled = rbUDP->Checked;
        TntLabel2->Enabled = rbUDP->Checked;
        Edit5->Enabled = rbUDP->Checked;
        TntLabel5->Enabled = rbServer->Checked;
        Edit6->Enabled = rbServer->Checked;
        TntLabel3->Enabled = rbServer->Checked;
        TntLabel6->Enabled = rbServer->Checked;
        cbWaitTime->Clear();
        int tempDelay = 0;
        if(rbSerial->Checked)
        {
            BPortClose->Caption = FormMain->LangFile.FormComportButton1;
            BPortCon->Caption = FormMain->LangFile.FormComportButton2;
            tempDelay = 3;
            if(FormMain->ComKind == 2)
                FormMain->WaitTime = FormMain->WaitTime / 2;
            FormMain->ComKind = 0;
            //if(FormMain->pcMessage->ActivePage == FormMain->tsEnv)
            //{
                FormMain->laAddress->Visible = FormMain->bRS485;
                FormMain->cbDIBDAddress->Visible = FormMain->laAddress->Visible;
                FormMain->tnSignAddr->Visible = FormMain->laAddress->Visible;
            //}
        }
        else
        {
            BPortClose->Caption = FormMain->LangFile.FormComportButton4;
            BPortCon->Caption = FormMain->LangFile.FormComportButton5;
            if(rbServer->Checked)
            {
                tempDelay = 10;
                if(FormMain->ComKind != 2)
                    FormMain->WaitTime = FormMain->WaitTime * 2;
            }
            else
            {
                tempDelay = 5;
                if(FormMain->ComKind == 2)
                    FormMain->WaitTime = FormMain->WaitTime / 2;
            }
            FormMain->laAddress->Visible = false;
            FormMain->cbDIBDAddress->Visible = FormMain->laAddress->Visible;
            FormMain->tnSignAddr->Visible = FormMain->laAddress->Visible;
            if(rbLAN->Checked)
                FormMain->ComKind = 1;
            else if(rbServer->Checked)
                FormMain->ComKind = 2;
            else if(rbUDP->Checked)
                FormMain->ComKind = 3;
        }
        for(int i = 1; i <= tempDelay; i++)
        {
            cbWaitTime->Items->Add(IntToStr(i));
        }
        cbWaitTime->ItemIndex = (FormMain->WaitTime / 1000) - 1;
        if(cbWaitTime->ItemIndex < 0)
            cbWaitTime->ItemIndex = 2;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::SearchComPort()
{
        // 시리얼 포트 찾는 함수
        //TRegistry *reg = new TRegistry;
        //TStringList *UnKey = new TStringList;
        ComBox1->Items->Clear();
        /*reg->RootKey = HKEY_LOCAL_MACHINE;
        if(reg->OpenKey("HARDWARE\\DEVICEMAP\\SERIALCOMM",false))
        {
            reg->GetValueNames(UnKey);
            for(int j = 0; j < UnKey->Count; j++)
            {
                if(reg->ValueExists( UnKey->Strings[j]))
                    ComBox1->Items->Add(reg->ReadString(UnKey->Strings[j]));
            }
            //ComBox1->Sorted = true;
            reg->CloseKey();
        }
        //if(UnKey->Count <= 0)
        //{
            for(int j = 1; j <= 99; j++)
                ComBox1->Items->Add("COM" + IntToStr(j));
            //ComBox1->Sorted = true;
        //}
        delete reg;
        delete UnKey;*/
        //ComBox1->Sorted = true;
        using namespace std;
        TRegistry *reg = new TRegistry(KEY_READ);
        TStringList *list = new TStringList;
        reg->RootKey = HKEY_LOCAL_MACHINE;
        reg->OpenKey("HARDWARE\\DEVICEMAP\\SERIALCOMM", false);
        reg->GetValueNames(list);

        for(int i = 0; i < list->Count; i++)
            ComBox1->Items->Add(reg->ReadString(list->Strings[i]));
        delete reg;
        delete list;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BSystemClick(TObject *Sender)
{
        // 윈도우 장치 관리자 실행 버튼
        HINSTANCE Ret;
        char szSystemPath[MAX_PATH];
        GetSystemDirectory(szSystemPath, MAX_PATH);
        AnsiString Path = AnsiString(szSystemPath);
        Ret = ShellExecute(0, "open", AnsiString(Path + "\\devmgmt.msc").c_str(), Path.c_str(), Path.c_str(), SW_SHOWNORMAL);
        if((int)Ret <= 32)
        {
            Ret = ShellExecute(0, "open", AnsiString(Path + "\\devmgmt.msc").c_str(), Path.c_str(), Path.c_str(), SW_SHOWNORMAL);
            if((int)Ret <= 32)
            {
                AnsiString asMessage = "devmgmt.msc File Not Open.";
                FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BPortCloseClick(TObject *Sender)
{
        try{
            if(FormMain->ComClose(FormMain->ComKind))
            {
                BPortClose->Enabled = false;
                BPortCon->Enabled = true;
            }
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BPortConClick(TObject *Sender)
{
        BPortCon->Enabled = false;
        if(rbLAN->Checked)
            FormMain->ComKind = 1;
        else if(rbServer->Checked)
            FormMain->ComKind = 2;
        else if(rbUDP->Checked)
            FormMain->ComKind = 3;
        else
            FormMain->ComKind = 0;
        if(!rbUDP->Checked)
        {
            if(FormMain->IdUDPServer->Active)
                FormMain->IdUDPServer->Active = false;
        }
        FormMain->PortUse1 = AnsiString(ComBox1->Text).TrimRight();
        FormMain->BaudRateUse1 = (eBaudRate)BaudBox1->ItemIndex;
        FormMain->IPAddress = AnsiString(Edit2->Text).TrimRight();
        FormMain->IPPort = StrToIntDef(AnsiString(Edit3->Text).TrimRight(), 5000);
        FormMain->ServerPort = StrToIntDef(AnsiString(Edit6->Text).TrimRight(), 5000);
        FormMain->UDPIPAddress = AnsiString(Edit4->Text).TrimRight();
        FormMain->DSPPort = StrToIntDef(AnsiString(Edit5->Text).TrimRight(), 5000);
        FormMain->bStopSend = false;
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, true))
        {
            BPortClose->Enabled = true;
            BPortCon->Enabled = false;
        }
        else
            BPortCon->Enabled = true;
        //BLEDSoftNet->Visible = FormMain->ComKind;
        FormMain->laAddress->Visible = FormMain->bRS485;
        //FormMain->laAddress2->Visible = FormMain->bRS485;
        FormMain->cbDIBDAddress->Visible = FormMain->bRS485;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::ComBox1ListBoxClick(TObject *Sender)
{
        ComBox1->Text = ComBox1->Items->Strings[ComBox1->ItemIndex];
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::ComBox1DropDown(TObject *Sender)
{
        SearchComPort();
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BLEDSoftNetClick(TObject *Sender)
{
        // 통신 설정창 열기 버튼
        FormMain->FDABITNet = new TFormDABITNet(this);
        FormMain->FDABITNet->Top = FormMain->Top;
        FormMain->FDABITNet->Left = FormMain->Left + (FormMain->Width - FormMain->FDABITNet->Width) / 2;
        FormMain->FDABITNet->ShowModal();
        if(FormMain->FDABITNet->Tag == 2)
        {
            if(FormMain->ComKind == 3)
            {}
            else
            {
                if(FormMain->FDABITNet->rbClient->Checked)
                {
                    rbServer->Checked = true;
                }
                else
                {
                    rbLAN->Checked = true;
                }
            }
            Edit2->Text = FormMain->FDABITNet->eIPAddr->Text;
            Edit4->Text = FormMain->FDABITNet->eIPAddr->Text;
            Edit3->Text = FormMain->FDABITNet->ePort->Text;
            //Edit5->Text = FormMain->FDABITNet->Edit7->Text;
            Edit6->Text = FormMain->FDABITNet->eServerPort->Text;
        }
        if(FormMain->FDABITNet)
        {
            delete FormMain->FDABITNet;
            FormMain->FDABITNet = NULL;
        }

        /*AnsiString destFolder = FormMain->BasePath;
        AnsiString destFile = destFolder + "DABITNet.exe";
        HINSTANCE Ret = ShellExecute(NULL, "open", destFile.c_str(), destFolder.c_str(), destFolder.c_str(), SW_SHOWNORMAL);
        if((int)Ret <= 32)
        {
            AnsiString asMessage = "DABITNet.exe File Not Open.";
            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
        }*/
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BBluetoothClick(TObject *Sender)
{
        FormMain->FBluetoothSet = new TFormBluetoothSet(this);
        FormMain->FBluetoothSet->Top = FormMain->Top;
        FormMain->FBluetoothSet->Left = FormMain->Left + (FormMain->Width - FormMain->FBluetoothSet->Width) / 2;
        FormMain->FBluetoothSet->ShowModal();
        if(FormMain->FBluetoothSet)
        {
            delete FormMain->FBluetoothSet;
            FormMain->FBluetoothSet = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BConnectClick(TObject *Sender)
{
        BConnect->Enabled = false;
        if(rbLAN->Checked)
            FormMain->ComKind = 1;
        else if(rbServer->Checked)
            FormMain->ComKind = 2;
        else if(rbUDP->Checked)
            FormMain->ComKind = 3;
        else
            FormMain->ComKind = 0;
        FormMain->PortUse1 = AnsiString(ComBox1->Text).TrimRight();
        FormMain->BaudRateUse1 = (eBaudRate)BaudBox1->ItemIndex;
        FormMain->IPAddress = AnsiString(Edit2->Text).TrimRight();
        FormMain->IPPort = StrToIntDef(AnsiString(Edit3->Text).TrimRight(), 5000);
        FormMain->ServerPort = StrToIntDef(AnsiString(Edit6->Text).TrimRight(), 5000);
        FormMain->UDPIPAddress = AnsiString(Edit4->Text).TrimRight();
        FormMain->DSPPort = StrToIntDef(AnsiString(Edit5->Text).TrimRight(), 5000);
        BYTE SendData[10];
        ZeroMemory(SendData, sizeof(SendData));
        for(int i = 0; i < 10; i++)
            SendData[i] = 0x30 + i;
        FormMain->bStopSend = false;
        int Addr = 0;
        bool bConnect = false;
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        OPCode tempOPC = DCT;
        int iSendSize = sizeof(SendData);
        AnsiString asMsg = "DABIT Connecting...";
        BPortClose->Enabled = false;
        BPortCon->Enabled = false;
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, BaudBox1->ItemIndex, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, true))
        {
            //BPortClose->Enabled = true;
            /*if(PorotocolType == 1)
            {
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "021";
                char Buf[3];
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%01d", SendData[0]);
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        FormMain->AddLog(asMsg + " [Fail]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(asMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(asMsg + " [Fail]" + FormMain->asLogMessage, clRed);
            }
            else
            {*/
                if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, tempOPC, true))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(asMsg + " [Fail]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(asMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(asMsg + " [Fail]" + FormMain->asLogMessage, clRed);
            //}
            if(FormMain->ComClose(FormMain->ComKind))
            {
                BPortClose->Enabled = false;
                BPortCon->Enabled = true;
            }
        }
        else
            BPortCon->Enabled = true;
        BConnect->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BitBtnOkClick(TObject *Sender)
{
        if(rbLAN->Checked)
            FormMain->ComKind = 1;
        else if(rbServer->Checked)
            FormMain->ComKind = 2;
        else if(rbUDP->Checked)
            FormMain->ComKind = 3;
        else
            FormMain->ComKind = 0;
        FormMain->PortUse1 = AnsiString(ComBox1->Text).TrimRight();
        FormMain->BaudRateUse1 = (eBaudRate)BaudBox1->ItemIndex;
        FormMain->bRS485 = cbRS485->Checked;
        FormMain->IPAddress = AnsiString(Edit2->Text).TrimRight();
        FormMain->IPPort = StrToIntDef(AnsiString(Edit3->Text).TrimRight(), 5000);
        FormMain->ServerPort = StrToIntDef(AnsiString(Edit6->Text).TrimRight(), 5000);
        FormMain->UDPIPAddress = AnsiString(Edit4->Text).TrimRight();
        FormMain->DSPPort = StrToIntDef(AnsiString(Edit5->Text).TrimRight(), 5000);
        //BLEDSoftNet->Visible = FormMain->ComKind;
        FormMain->laAddress->Visible = FormMain->bRS485;
        //FormMain->laAddress2->Visible = !FormMain->ComKind;
        if(FormMain->ComKind != 0)
        {
            FormMain->Controler1 = 0;
            FormMain->cbDIBDAddress->ItemIndex = FormMain->Controler1;
            cbDIBDAddress->ItemIndex = FormMain->Controler1;
        }
        FormMain->cbDIBDAddress->Visible = FormMain->bRS485;
        FormMain->WaitTime = StrToIntDef(cbWaitTime->Text, 4) * 1000;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::pnCaptionMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            OldXPos = X;
            OldYPos = Y;
            bCaptionMove = true;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::pnCaptionMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            bCaptionMove = false;
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::cbWaitTimeClick(TObject *Sender)
{
        FormMain->WaitTime = StrToIntDef(cbWaitTime->Text, 4) * 1000;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::BSearchClick(TObject *Sender)
{
        BSearch->Enabled = false;
        int Addr = 0;
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        FormMain->PortUse1 = AnsiString(ComBox1->Text).TrimRight();
        BYTE SendData[9];
        int iSendSize = 1;
        ZeroMemory(SendData, sizeof(SendData));
        AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "021";
        char Buf[3];
        ZeroMemory(Buf, sizeof(Buf));
        SendData[0] = PowerOn;
        wsprintf(Buf, "%01d", SendData[0]);
        asSendData = asSendData + AnsiString(Buf) + "!]";
        iSendSize = asSendData.Length();
        memcpy(SendData, asSendData.c_str(), iSendSize);
        for(int i = 2; i < 10; i++)
        {
            BaudBox1->ItemIndex = i;
            if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, BaudBox1->ItemIndex, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, true))
            {
                BPortClose->Enabled = true;
                BPortCon->Enabled = false;
                if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, false))
                {
                    FormMain->AddLog(FormMain->ComPort1->Port + " : " + BaudBox1->Text + "bps ComPort Search [Success]", clBlue);
                    break;
                }
            }
            else
            {
                FormMain->AddLog("ComPort Search [Fail]", clRed);
                break;
            }
        }
        FormMain->NoneDelayCount(500);
        BPortClose->Click();
        BSearch->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::cbRS485Click(TObject *Sender)
{
        if(Sender == laRS485)
            cbRS485->Checked = !cbRS485->Checked;
        FormMain->bRS485 = cbRS485->Checked;
        cbDIBDAddress->Visible = cbRS485->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFormComPort::cbDIBDAddressClick(TObject *Sender)
{
        // 전공판 어드레스 값
        FormMain->Controler1 = cbDIBDAddress->ItemIndex;
        FormMain->cbDIBDAddress->ItemIndex = FormMain->Controler1;
}
//---------------------------------------------------------------------------
