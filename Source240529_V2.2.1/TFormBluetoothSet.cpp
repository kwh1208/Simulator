//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormBluetoothSet.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma link "TntStdCtrls"
#pragma link "CPort"
#pragma link "MMTimer"
#pragma resource "*.dfm"
TFormBluetoothSet *FormBluetoothSet;
//---------------------------------------------------------------------------
__fastcall TFormBluetoothSet::TFormBluetoothSet(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption = FormMain->LangFile.FormBluetoothCaption;
        pnCaption->Caption = " " + this->Caption;
        laBTLabel1->Caption = FormMain->LangFile.FormBluetoothLabel1;
        laBTLabel2->Caption = FormMain->LangFile.FormBluetoothLabel2;
        laBTLabel3->Caption = FormMain->LangFile.FormBluetoothLabel3;
        BtnDelaySend->Caption = FormMain->LangFile.FormBluetoothButton1;
        BtnSendSearch->Caption = FormMain->LangFile.FormBluetoothButton2;
        BtnSendSet->Caption = FormMain->LangFile.FormBluetoothButton3;
        BtnSendBegin->Caption = FormMain->LangFile.FormBluetoothButton4;
        BtnSendEnd->Caption = FormMain->LangFile.FormBluetoothButton5;
        BitBtnOk->Caption = FormMain->LangFile.MessageButton10;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;
        gnBlutooth->Color = FormMain->MainRectangleColor;
        pnBlutooth->Color = FormMain->MainColor;
        laBTLabel1->Color = FormMain->MainColor;
        laBTLabel1->Font->Color = FormMain->TextColor;
        laBTLabel1->Font->Name = FormMain->MainFont;
        laBTLabel1->Font->Size = FormMain->MainFontSize;
        laBTLabel2->Color = FormMain->MainColor;
        laBTLabel2->Font->Color = FormMain->TextColor;
        laBTLabel2->Font->Name = FormMain->MainFont;
        laBTLabel2->Font->Size = FormMain->MainFontSize;
        laBTLabel3->Color = FormMain->MainColor;
        laBTLabel3->Font->Color = FormMain->TextColor;
        laBTLabel3->Font->Name = FormMain->MainFont;
        laBTLabel3->Font->Size = FormMain->MainFontSize;
        BtnDelaySend->Font->Color = FormMain->TextBGColor;
        BtnDelaySend->Font->Name = FormMain->MainFont;
        BtnDelaySend->Font->Size = FormMain->MainFontSize;
        pnBtnDelaySend->Color = FormMain->ButtonColor;
        BtnSendSearch->Font->Color = FormMain->TextBGColor;
        BtnSendSearch->Font->Name = FormMain->MainFont;
        BtnSendSearch->Font->Size = FormMain->MainFontSize;
        pnBtnSendSearch->Color = FormMain->ButtonColor;
        BtnSendSet->Font->Color = FormMain->TextBGColor;
        BtnSendSet->Font->Name = FormMain->MainFont;
        BtnSendSet->Font->Size = FormMain->MainFontSize;
        pnBtnSendSet->Color = FormMain->ButtonColor;
        BtnSendBegin->Font->Color = FormMain->TextBGColor;
        BtnSendBegin->Font->Name = FormMain->MainFont;
        BtnSendBegin->Font->Size = FormMain->MainFontSize;
        pnBtnSendBegin->Color = FormMain->ButtonColor;
        BtnSendEnd->Font->Color = FormMain->TextBGColor;
        BtnSendEnd->Font->Name = FormMain->MainFont;
        BtnSendEnd->Font->Size = FormMain->MainFontSize;
        pnBtnSendEnd->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------
void __fastcall TFormBluetoothSet::FormCreate(TObject *Sender)
{
        eBluetoothName->Text = (WideString)FormMain->BluetoothName;
        eBluetoothPassword->Text = (WideString)FormMain->BluetoothPassword;
        eBluetoothDelayTime->Text = IntToStr(FormMain->BluetoothDelayTime);
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::BtnSendSearchClick(TObject *Sender)
{
        int Addr = 0;
        AnsiString asData = "";
        AnsiString asBluetoothData = "";
        Addr = FormMain->Controler1;
        BtnSendSearch->Enabled = false;
        BtnSendSet->Enabled = false;
        BtnSendBegin->Enabled = false;
        BtnSendEnd->Enabled = false;
        FormMain->bStopSend = false;
        AnsiString asMsg = "";
        int DataSize = 0;
        if(Sender == BtnSendSearch)
        {
            asBluetoothData = "++SET++![BT SEARCHING DIBD!]";
            asMsg = "Bluetooth Searching";
            BluetoothIndex = 7;
        }
        else if(Sender == BtnSendBegin)
        {
            asBluetoothData = "++SET++![BT ";
            asMsg = eBluetoothPassword->Text.SubString(1, 20);
            DataSize = asMsg.Length();
            if(DataSize < 20)
            {
                for(int i = DataSize; i < 20; i++)
                    asMsg = asMsg.Insert(" ", asMsg.Length() + 1);
            }
            asBluetoothData = asBluetoothData + asMsg + " BEGIN!]";
            asMsg = "Bluetooth BeginBLE";
            BluetoothIndex = 14;
        }
        else if(Sender == BtnSendEnd)
        {
            asBluetoothData = "++SET++![BT ";
            asMsg = eBluetoothPassword->Text.SubString(1, 20);
            DataSize = asMsg.Length();
            if(DataSize < 20)
            {
                for(int i = DataSize; i < 20; i++)
                    asMsg = asMsg.Insert(" ", asMsg.Length() + 1);
            }
            asBluetoothData = asBluetoothData + asMsg + " END!]";
            asMsg = "Bluetooth EndBLE";
            BluetoothIndex = 15;
        }
        else
        {
            asBluetoothData = "++SET++![BT SETT  ";
            //if(cbBluetoothMode->ItemIndex == 0)
            //    asBluetoothData = asBluetoothData + "30  ";
            //else
                asBluetoothData = asBluetoothData + "31  ";
            asMsg = eBluetoothName->Text.SubString(1, 20);
            DataSize = asMsg.Length();
            if(DataSize < 20)
            {
                for(int i = DataSize; i < 20; i++)
                    asMsg = asMsg.Insert(" ", asMsg.Length() + 1);
            }
            asBluetoothData = asBluetoothData + asMsg + "  ";
            asMsg = eBluetoothPassword->Text.SubString(1, 20);
            DataSize = asMsg.Length();
            if(DataSize < 20)
            {
                for(int i = DataSize; i < 20; i++)
                    asMsg = asMsg.Insert(" ", asMsg.Length() + 1);
            }
            asBluetoothData = asBluetoothData + asMsg + "!]";
            asMsg = "Bluetooth Setting";
            BluetoothIndex = 8;
        }
        DataSize = asBluetoothData.Length();
        BYTE *Data = new BYTE[DataSize];
        ZeroMemory(Data, DataSize);
        memcpy(Data, asBluetoothData.c_str(), DataSize);
        if(ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            try{
                //FormMain->NoneDelayCount(500);
                iSerialPos = 0;
                ComPort1->Write(Data, DataSize);
                //if(FormMain->ShowDibdnetLogView)
                //{
                    AnsiString asMac = "";
                    for(int i = 0; i < DataSize; i++)
                        asMac = asMac + FormMain->Int2HexConvert(Data[i]) + " ";
                    asMac = asMac + "\r\n" + AnsiString((char *)Data).SubString(1, DataSize);
                    FormMain->AddLog("Serial Send Data : " + asMac, clBlue);
                //}
            }catch(...){
            }
            /*if(!FormMain->ModuleASCIIData(FormMain->ComKind, Addr, Data, DataSize, true))
                FormMain->AddLog(asMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
            else
                FormMain->AddLog(asMsg + " [OK]" + FormMain->asLogMessage, clBlack);*/
            //ComClose(FormMain->ComKind);
        }
        if(Data)
        {
            delete [] Data;
            Data = NULL;
        }
        BtnSendSearch->Enabled = true;
        BtnSendSet->Enabled = true;
        BtnSendBegin->Enabled = true;
        BtnSendEnd->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::BitBtnOkClick(TObject *Sender)
{
        FormMain->BluetoothName = eBluetoothName->Text;
        FormMain->BluetoothPassword = eBluetoothPassword->Text;
        FormMain->BluetoothDelayTime = StrToIntDef(eBluetoothDelayTime->Text, 0);
        Close();
}
//---------------------------------------------------------------------------

bool __fastcall TFormBluetoothSet::ComPortSet(BYTE ComKind, AnsiString PortUse, BYTE BaudRateUse, BYTE DTR, BYTE RTS, AnsiString IPAddress, int IPPort, bool bMessage)
{
        AnsiString asMessage = "";
        //if(ComKind == 0 || (ComKind == 1 && ClientSocket1->Socket->Connected))
            ComClose(ComKind);
        bool tempMsg = false;
        //if(ComKind == 0)
        //{
            ComPort1->Port = PortUse;
            AnsiString asBR = "";
            switch(BaudRateUse)
            {
                case 0 :
                    ComPort1->BaudRate = br2400;
                    asBR = ": 2,400bps";
                    break;
                case 1 :
                    ComPort1->BaudRate = br4800;
                    asBR = ": 4,800bps";
                    break;
                case 2 :
                    ComPort1->BaudRate = br9600;
                    asBR = ": 9,600bps";
                    break;
                case 3 :
                    ComPort1->BaudRate = br19200;
                    asBR = ": 19,200bps";
                    break;
                case 4 :
                    ComPort1->BaudRate = br38400;
                    asBR = ": 38,400bps";
                    break;
                case 5 :
                    ComPort1->BaudRate = br57600;
                    asBR = ": 57,600bps";
                    break;
                case 6 :
                    ComPort1->BaudRate = br115200;
                    asBR = ": 115,200bps";
                    break;
                case 7 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 230400;
                    asBR = ": 230,400bps";
                    break;
                case 8 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 460800;
                    asBR = ": 460,800bps";
                    break;
                case 9 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 921600;
                    asBR = ": 921,600bps";
                    break;
                case 10 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 1843200;
                    asBR = ": 1,843,200bps";
                    break;
                case 11 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 3686400;
                    asBR = ": 3,686,400bps";
                    break;
                case 12 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 7372800;
                    asBR = ": 7,372,800bps";
                    break;
                case 13 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 14745600;
                    asBR = ": 14,745,600bps";
                    break;
                case 14 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 29491200;
                    asBR = ": 29,491,200bps";
                    break;
                default :
                    ComPort1->BaudRate = br115200;
                    asBR = ": 115,200bps";
                    break;
            }
            switch(DTR)
            {
                case 0 :
                    ComPort1->FlowControl->ControlDTR = dtrDisable;
                    break;
                case 1 :
                    ComPort1->FlowControl->ControlDTR = dtrEnable;
                    break;
                case 2 :
                    ComPort1->FlowControl->ControlDTR = dtrHandshake;
                    break;
                default :
                    ComPort1->FlowControl->ControlDTR = dtrDisable;
                    break;
            }
            switch(RTS)
            {
                case 0 :
                    ComPort1->FlowControl->ControlRTS = rtsDisable;
                    break;
                case 1 :
                    ComPort1->FlowControl->ControlRTS = rtsEnable;
                    break;
                case 2 :
                    ComPort1->FlowControl->ControlRTS = rtsHandshake;
                    break;
                case 3 :
                    ComPort1->FlowControl->ControlRTS = rtsToggle;
                    break;
                default :
                    ComPort1->FlowControl->ControlRTS = rtsDisable;
                    break;
            }
            if(!ComPort1->Connected)
            {
                try{
                    ComPort1->Open();
                    asMessage = ComPort1->Port + " " + asBR + ", 8, 1, N - Open [OK]";
                    FormMain->AddLog(asMessage, clBlack);
                }catch(...){
                    asMessage = ComPort1->Port + " " + asBR + ", 8, 1, N - Open [FAIL]";
                    FormMain->AddLog(asMessage, clRed);
                    if(bMessage)
                        MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                    return false;
                }
            }
        /*}
        else
        {
            try{
                bDDNS = false;
                int tempIP = StrToIntDef(IPAddress.SubString(1, IPAddress.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(bDDNS)
                {
                    try{
                        SchBmpFile = false;
                        DNSAddress = "";
                        //bStopSend = false;
                        IdIcmpClient1->Host = IPAddress;
                        IdIcmpClient1->ReceiveTimeout = 1000;
                        DNSConnect = false;
                        IdIcmpClient1->Ping();
                        LANDelayCount(3000);
                        if(DNSAddress == "" || DNSAddress == " ")
                        {
                            IdDNSResolver->QueryRecords = IdDNSResolver->QueryRecords<<qtA;
                            IdDNSResolver->Host = GetDNS();
                            IdDNSResolver->Active = true;
                            IdDNSResolver->Resolve(IPAddress);
                            IdDNSResolver->Active = false;
                            for(int i = 0; i < IdDNSResolver->QueryResult->Count; i++)
                            {
                                switch(IdDNSResolver->QueryResult->Items[i]->RecType)
                                {
                                    case qtA:
                                    {
                                        DNSAddress = dynamic_cast<TARecord *>(IdDNSResolver->QueryResult->Items[i])->IPAddress;
                                        DNSConnect = true;
                                    }
                                    break;
                                }
                                if(DNSConnect)
                                    break;
                            }
                        }
                    }catch(...){
                        tempMsg = true;
                        try{
                            if(DNSAddress == "" || DNSAddress == " ")
                            {
                                IdDNSResolver->QueryRecords = IdDNSResolver->QueryRecords<<qtA;
                                IdDNSResolver->Host = GetDNS();
                                IdDNSResolver->Active = true;
                                IdDNSResolver->Resolve(IPAddress);
                                IdDNSResolver->Active = false;
                                for(int i = 0; i < IdDNSResolver->QueryResult->Count; i++)
                                {
                                    switch(IdDNSResolver->QueryResult->Items[i]->RecType)
                                    {
                                        case qtA:
                                        {
                                            DNSAddress = dynamic_cast<TARecord *>(IdDNSResolver->QueryResult->Items[i])->IPAddress;
                                            DNSConnect = true;
                                            ShowMessage("ping true 3" + DNSAddress);
                                        }
                                        break;
                                    }
                                    if(DNSConnect)
                                        break;
                                }
                                tempMsg = false;
                            }
                        }catch(...){
                            if(bMessage)
                            {
                                asMessage = "IP:" + DNSAddress + ", PORT:" + IntToStr(IPPort) + " Open [FAIL]";
                                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                            }
                            DNSConnect = false;
                            DNSAddress = "";
                            return false;
                        }
                        if(tempMsg && bMessage)
                        {
                            asMessage = "IP:" + DNSAddress + ", PORT:" + IntToStr(IPPort) + " Open [FAIL]";
                            MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                        }
                        if(tempMsg)
                        {
                            DNSConnect = false;
                            DNSAddress = "";
                            return false;
                        }
                    }
                    if(DNSConnect)
                    {
                        ClientSocket1->Address = DNSAddress;
                        ClientSocket1->Port = IPPort;
                        DNSConnect = false;
                    }
                    else
                    {
                        ClientSocket1->Address = "127.0.0.1";
                        ClientSocket1->Port = IPPort;
                    }
                }
                else
                {
                    ClientSocket1->Address = IPAddress;
                    ClientSocket1->Port = IPPort;
                }
            }catch(...){
                asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Open [FAIL]";
                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                return false;
            }
            try{
                DNSConnect = false;
                ClientSocket1->Open();
                LANDelayCount(ServerCommOpenDelay);
                if(!DNSConnect)
                {
                    if(bMessage)
                    {
                        asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Open [FAIL]";
                        MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                    }
                    return false;
                }
                else
                    DNSConnect = false;
            }catch(...){
                if(bMessage)
                {
                    asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Open [FAIL]";
                    MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                }
                return false;
            }
        }*/
        return true;
}
//---------------------------------------------------------------------------

bool __fastcall TFormBluetoothSet::ComClose(BYTE ComKind)
{
        if(FormMain->DelayRun == 1 && FormMain->DelayTime == 0)
            FormMain->NoneDelayCount(300);
        AnsiString asMessage = "";
        //if(ComKind == 0)
        //{
            try{
                ComPort1->Close();
                asMessage = ComPort1->Port + " Close [OK]";
                FormMain->AddLog(asMessage, clBlack);
            }catch(...){
                asMessage = ComPort1->Port + " Close [FAIL]";
                FormMain->AddLog(asMessage, clRed);
                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                return false;
            }
        /*}
        else
        {
            try{
                ClientSocket1->Close();
                asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Close [OK]";
                FormMain->AddLog(asMessage, clBlack);
            }catch(...){
                asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Close [FAIL]";
                FormMain->AddLog(asMessage, clRed);
                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                return false;
            }
        }*/
        return true;
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::ComPort1RxChar(TObject *Sender,
      int Count)
{
        int Size = Count;
        char *Buffer = new char[Size];
        ZeroMemory(Buffer, Size);
        ComPort1->Read(Buffer, Size);
        //if(!bSerial)
        //{
            if(iSerialPos == 0)
            {
                SerialMMTimer->Interval = 1000;
                SerialMMTimer->Enabled = true;
            }
            memcpy(&RedData[iSerialPos], Buffer, Size);
            if(BluetoothIndex == 14 || BluetoothIndex == 15)
            {
                if(iSerialPos + Size >= 10)
                    iRemain = (iSerialPos + Size) % 10;
                iSerialPos = iSerialPos + Size;
                if(iSerialPos >= 10)
                {
                    SerialMMTimer->Enabled = false;
                    ReadParsing(false, 0, RedData, iSerialPos);
                    ZeroMemory(RedData, sizeof(RedData));
                    iSerialPos = iSerialPos % 10;
                    memcpy(RedData, &Buffer[Size - iRemain], iRemain);
                    SerialMMTimer->Enabled = true;
                }
            }
            else
            {
                if(iSerialPos + Size >= 48)
                    iRemain = (iSerialPos + Size) % 48;
                iSerialPos = iSerialPos + Size;
                if(iSerialPos >= 48)
                {
                    SerialMMTimer->Enabled = false;
                    ReadParsing(false, 0, RedData, iSerialPos);
                    ZeroMemory(RedData, sizeof(RedData));
                    iSerialPos = iSerialPos % 48;
                    memcpy(RedData, &Buffer[Size - iRemain], iRemain);
                    SerialMMTimer->Enabled = true;
                }
            }
        //}
        if(Buffer)
        {
            delete [] Buffer;
            Buffer = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::SerialMMTimerTimer(TObject *Sender)
{
        SerialMMTimer->Enabled = false;
        //bSerial = false;
        iSerialPos = 0;
        iRemain = 0;
        //RecieveData(RedData);
        ZeroMemory(RedData, sizeof(RedData));
        if(FormMain->ComKind == 0)
        {
            ComClose(FormMain->ComKind);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::ReadParsing(bool ComKind, char Add, BYTE *ParsingData, int ParsingSize)
{
        //AnsiString tempData = AnsiString((char*)ParsingData).SubString(12, 2);
        int iIndex = 0;
        if(AnsiString((char *)ParsingData).Pos("BLE RX:"))
            iIndex = 26;
        if(BluetoothIndex == 7)
        {
            //if(tempData == "30")
            //    cbBluetoothMode->ItemIndex = 0;
            //else
            //    cbBluetoothMode->ItemIndex = 1;
            eBluetoothName->Text = AnsiString((char*)ParsingData).SubString(iIndex + 16, 20).TrimRight();
            eBluetoothPassword->Text = AnsiString((char*)ParsingData).SubString(iIndex + 38, 20).TrimRight();
        }
        //if(FormMain->ShowDibdnetLogView)
        //{
            AnsiString asMac = "";
            //if(FormMain->ComKind == 0)
            //{
                int itempSize = ParsingSize;
                if(BluetoothIndex == 14 || BluetoothIndex == 15)
                {
                    if(itempSize >= 10)
                        itempSize = 10;
                }
                else
                {
                    if(itempSize >= 48)
                        itempSize = 48;
                }
                for(int i = 0; i < ParsingSize; i++)
                      asMac = asMac + FormMain->Int2HexConvert(ParsingData[i]) + " ";
                asMac = asMac + "\r\n" + AnsiString((char*)ParsingData).SubString(1, ParsingSize);
                FormMain->AddLog("Serial Recieve Data : " + asMac, clBlue);
            //}
        //}
        //SchBmpFile = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormBluetoothSet::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormBluetoothSet::pnCaptionMouseUp(TObject *Sender,
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

