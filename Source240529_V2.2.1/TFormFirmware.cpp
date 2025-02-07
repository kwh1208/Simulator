//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormFirmware.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntStdCtrls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormFirmware *FormFirmware;
//---------------------------------------------------------------------------
__fastcall TFormFirmware::TFormFirmware(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption           = FormMain->LangFile.FormFirmware;
        pnCaption->Caption = " " + this->Caption;
        BVerCheck->Caption      = FormMain->LangFile.MessageButton15;
        BitBtnOk->Caption       = FormMain->LangFile.MessageButton10;
        DataTransfer->Caption   = FormMain->LangFile.BtnSend;
        FileOpen->Caption       = FormMain->LangFile.MessageButton21;
        TeLabel1->Caption       = "Controller firmware information :";
        TeLabel2->Caption       = "Firmware file information to be updated :";
        TntLabel2->Caption      = FormMain->LangFile.Message14;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        TeLabel1->Font->Color = FormMain->TextColor;
        TeLabel1->Font->Name = FormMain->MainFont;
        TeLabel1->Font->Size = 11;//FormMain->MainFontSize;
        TeLabel2->Font->Color = FormMain->TextColor;
        TeLabel2->Font->Name = FormMain->MainFont;
        TeLabel2->Font->Size = 11;//FormMain->MainFontSize;
        Label1->Font->Color = FormMain->TextColor;
        Label1->Font->Name = FormMain->MainFont;
        Label1->Font->Size = FormMain->MainFontSize;
        Label2->Font->Color = FormMain->TextColor;
        Label2->Font->Name = FormMain->MainFont;
        Label2->Font->Size = FormMain->MainFontSize;
        TntLabel2->Font->Color = FormMain->TextColor;
        TntLabel2->Font->Name = FormMain->MainFont;
        TntLabel2->Font->Size = FormMain->MainFontSize;
        ePath->Font->Name = FormMain->MainFont;
        ePath->Font->Size = FormMain->MainFontSize;
        BVerCheck->Font->Color = FormMain->TextBGColor;
        BVerCheck->Font->Name = FormMain->MainFont;
        BVerCheck->Font->Size = FormMain->MainFontSize;
        pnBVerCheck->Color = FormMain->ButtonColor;
        FileOpen->Font->Color = FormMain->TextBGColor;
        FileOpen->Font->Name = FormMain->MainFont;
        FileOpen->Font->Size = FormMain->MainFontSize;
        pnFileOpen->Color = FormMain->ButtonColor;
        DataTransfer->Font->Color = FormMain->TextBGColor;
        DataTransfer->Font->Name = FormMain->MainFont;
        DataTransfer->Font->Size = FormMain->MainFontSize;
        pnDataTransfer->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::FormCreate(TObject *Sender)
{
        AnsiString asFirmFileData = "";
        int iPos = 0;
        TFileStream *File = NULL;
        if(FileExists(FormMain->LastFirmwarePathFile))
        {
            BYTE *FirmwareData = NULL;
            AnsiString asMsg = "";
            try{
                File = new TFileStream(FormMain->LastFirmwarePathFile, fmOpenRead);
                if(!File->Size)
                {
                    if(File)
                    {
                        delete File;
                        File = NULL;
                    }
                    asMsg = ExtractFileName(FormMain->LastFirmwarePathFile) + " " + FormMain->LangFile.Message15;
                    return;
                }   
                int iDataSize = File->Size;
                FirmwareData = new BYTE[iDataSize];
                ZeroMemory(FirmwareData, iDataSize);
                File->Read(FirmwareData, iDataSize);
                //asMsg = "Firmware Information :\r\n";
                //Label1->Caption = "";
                Label2->Caption = "";
                FirmwareKind = 0x00;
                char Buf[4];
                if(FirmwareData[0x204] == 'D' && FirmwareData[0x205] == 'I' && FirmwareData[0x206] == 'B' && FirmwareData[0x207] == 'D')
                {
                    FirmwareKind = 2;
                    FirmwareBoardKind = FirmwareData[0x203];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x204]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x215]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
                else if(FirmwareData[0x104] == 'D' && FirmwareData[0x105] == 'I' && FirmwareData[0x106] == 'B' && FirmwareData[0x107] == 'D')
                {
                    FirmwareKind = 0;
                    FirmwareBoardKind = FirmwareData[0x103];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x104]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x115]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
                else if(FirmwareData[0x284] == 'D' && FirmwareData[0x285] == 'I' && FirmwareData[0x286] == 'B' && FirmwareData[0x287] == 'D')
                {
                    FirmwareKind = 3;
                    FirmwareBoardKind = FirmwareData[0x283];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x284]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x295]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
                else if(FirmwareData[0x50] == 'D' && FirmwareData[0x51] == 'I' && FirmwareData[0x52] == 'B' && FirmwareData[0x53] == 'D')
                {
                    FirmwareKind = 4;
                    asFirmFileData = AnsiString((char *)&FirmwareData[0x50]).SubString(1, 9) + " V" + AnsiString((char *)&FirmwareData[0x30]).SubString(1, 16);
                    Label2->Caption = asFirmFileData;
                }
                else
                {
                    FirmwareKind = 1;
                    FirmwareBoardKind = FirmwareData[0x203];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x204]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x215]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
            }catch(...){
            }
            if(FirmwareData)
            {
                delete [] FirmwareData;
                FirmwareData = NULL;
            }
            if(File)
            {
                delete File;
                File = NULL;
            }
        }
        ePath->Text      = FormMain->LastFirmwarePathFile;
        ePath->SelStart = ePath->Text.Length();
        ePath->SelLength = 0;
        DataTransfer->Visible = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::FileOpenClick(TObject *Sender)
{
        TFileStream *File = NULL;
        BYTE *FirmwareData = NULL;
        int iDataSize = 0;
        int iPos = 0;
        AnsiString asFirmFileData = "";
        AnsiString asMsg = "";
        try
        {
            FormMain->OpenDialog->Options.Clear();
            FormMain->OpenDialog->Files->Clear();
            FormMain->OpenDialog->Options << ofFileMustExist << ofNoChangeDir;
            FormMain->OpenDialog->FilterIndex = 1;
            FormMain->OpenDialog->Filter = "Firmware File (*.bin)|*.bin|All files (*.*)|*.*";
            FormMain->OpenDialog->DefaultExt = "bin";
            FormMain->OpenDialog->Title = "Open Firmware File";
            FormMain->OpenDialog->FileName = "";
            FormMain->OpenDialog->InitialDir = FormMain->BasePath + FormMain->DestFirmwarePath;
            if(FormMain->LastFirmwarePathFile != "" && FormMain->LastFirmwarePathFile != " ")
            {
                if(FileExists(FormMain->LastFirmwarePathFile))
                    FormMain->OpenDialog->FileName = FormMain->LastFirmwarePathFile;
            }
            if(FormMain->OpenDialog->Execute())
            {
                File = new TFileStream(FormMain->OpenDialog->FileName, fmOpenRead | fmShareCompat);
                if(!File->Size)
                {
                    if(File)
                    {
                        delete File;
                        File = NULL;
                    }
                    asMsg = ExtractFileName(FormMain->OpenDialog->FileName) + " " + FormMain->LangFile.Message15;
                    return;
                }
                iDataSize = File->Size;
                FirmwareData = new BYTE[iDataSize];
                ZeroMemory(FirmwareData, iDataSize);
                File->Read(FirmwareData, iDataSize);
                //asMsg = "Firmware Information :\r\n";
                //Label1->Caption = "";
                Label2->Caption = "";
                FirmwareKind = 0x00;
                char Buf[4];
                if(FirmwareData[0x204] == 'D' && FirmwareData[0x205] == 'I' && FirmwareData[0x206] == 'B' && FirmwareData[0x207] == 'D')
                {
                    FirmwareKind = 2;
                    FirmwareBoardKind = FirmwareData[0x203];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x204]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x215]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
                else if(FirmwareData[0x104] == 'D' && FirmwareData[0x105] == 'I' && FirmwareData[0x106] == 'B' && FirmwareData[0x107] == 'D')
                {
                    FirmwareKind = 0;
                    FirmwareBoardKind = FirmwareData[0x103];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x104]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x115]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
                else if(FirmwareData[0x284] == 'D' && FirmwareData[0x285] == 'I' && FirmwareData[0x286] == 'B' && FirmwareData[0x287] == 'D')
                {
                    FirmwareKind = 3;
                    FirmwareBoardKind = FirmwareData[0x283];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x284]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x295]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
                else if(FirmwareData[0x50] == 'D' && FirmwareData[0x51] == 'I' && FirmwareData[0x52] == 'B' && FirmwareData[0x53] == 'D')
                {
                    FirmwareKind = 4;
                    asFirmFileData = AnsiString((char *)&FirmwareData[0x50]).SubString(1, 9) + " V" + AnsiString((char *)&FirmwareData[0x30]).SubString(1, 16);
                    Label2->Caption = asFirmFileData;
                }
                else
                {
                    FirmwareKind = 1;
                    FirmwareBoardKind = FirmwareData[0x203];
                    ZeroMemory(Buf, sizeof(Buf));
                    sprintf(Buf, "%03d", FirmwareBoardKind);
                    asFirmFileData = "<" + AnsiString(Buf) + "> " + AnsiString((char *)&FirmwareData[0x204]).SubString(1, 17) + AnsiString((char *)&FirmwareData[0x215]).SubString(1, 27);
                    //Label2->Caption = asFirmFileData;
                    iPos = asFirmFileData.Pos(" V");
                    asMsg = asFirmFileData.SubString(1, iPos) + "\r\n";
                    Label2->Caption = Label2->Caption + asMsg;
                    asMsg = asFirmFileData.SubString(iPos + 1, asFirmFileData.Length() - iPos);
                    Label2->Caption = Label2->Caption + asMsg;
                }
                FormMain->LastFirmwarePathFile = FormMain->OpenDialog->FileName;
                ePath->Text = FormMain->LastFirmwarePathFile;
                ePath->SelStart = ePath->Text.Length();
                ePath->SelLength = 0;
            }
        }
        catch(...)
        {
            asMsg = ExtractFileName(FormMain->OpenDialog->FileName) + " " + FormMain->LangFile.Message15;
            FormMain->Message("\r\n" + asMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMsg.Length() - 2, 3, 1, clRed);
        }
        if(File)
        {
            delete File;
            File = NULL;
        }
        if(FirmwareData)
        {
            delete [] FirmwareData;
            FirmwareData = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::BVerCheckClick(TObject *Sender)
{
        FormMain->bStopSend = false;
        int Addr = 0;
        AnsiString Data = "";
        try{
            if(FormMain->bRS485)
                Addr = FormMain->Controler1;
        }catch(...){
            return;
        }
        BVerCheck->Enabled = false;
        bool bTempEnable = DataTransfer->Enabled;
        DataTransfer->Enabled = false;
        bool bConnect = false;
        AnsiString TmpMsg = "DABIT Version Request [Receive:";
        BYTE SendData[8];
        int iSendSize = 1;
        ZeroMemory(SendData, sizeof(SendData));
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, true))
        {
            if(FormMain->PorotocolType == 1)
            {
                AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "081!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                bConnect = FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, true);
            }
            else
            {
                SendData[0] = 0xF1;
                bConnect = FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, (OPCode)FI, true);
            }
            if(!bConnect)
                FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
            else
            {
                DataTransfer->Enabled = true;
                FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        else
            FormMain->AddLog(FormMain->LangFile.Message15, clRed);
        BVerCheck->Enabled = true;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::DataTransferClick(TObject *Sender)
{
        try{
            AnsiString asMsg = "";
            int iDataSize = 0;
            BYTE *FirmwareData = NULL;
            if(FileExists(FormMain->LastFirmwarePathFile))
            {
                TFileStream *File = NULL;
                try{
                    File = new TFileStream(FormMain->LastFirmwarePathFile, fmOpenRead | fmShareCompat);
                    if(!File->Size)
                    {
                        if(File)
                        {
                            delete File;
                            File = NULL;
                        }
                        asMsg = ExtractFileName(FormMain->LastFirmwarePathFile) + " " + FormMain->LangFile.Message15;
                        FormMain->Message("\r\n" + asMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMsg.Length() - 2, 3, 1, clBlack);
                        return;
                    }
                    iDataSize = File->Size;
                    FirmwareData = new BYTE[iDataSize];
                    ZeroMemory(FirmwareData, iDataSize);
                    File->Read(FirmwareData, iDataSize);
                    if(File)
                    {
                        delete File;
                        File = NULL;
                    }
                }
                catch(...)
                {
                    if(File)
                    {
                        delete File;
                        File = NULL;
                    }
                    asMsg = ExtractFileName(FormMain->LastFirmwarePathFile) + " " + FormMain->LangFile.Message15;
                    FormMain->Message("\r\n" + asMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMsg.Length() - 2, 3, 1, clBlack);
                    return;
                }
            }
            else
            {
                asMsg = ExtractFileName(FormMain->LastFirmwarePathFile) + " " + FormMain->LangFile.Message15;
                FormMain->Message("\r\n" + asMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMsg.Length() - 2, 3, 1, clBlack);
                return;
            }
            bool bComp = false;
            BYTE SendData[6];
            ZeroMemory(SendData, sizeof(SendData));
            SendData[0] = 0xF1;
            SendData[1] = FormMain->ProgramType;
            SendData[2] = StrToIntDef("0x" + FormMain->VerMj[0], 0);
            SendData[3] = StrToIntDef("0x" + FormMain->VerMj[1] + FormMain->VerMj[2], 0);
            SendData[4] = (BYTE)char('N');
            this->Enabled = false;
            DataTransfer->Enabled = false;
            int PacketSize = 1024;
            bool bConnected = false;
            int FUDCnt = 0;
            int tempSize = 0;
            if(iDataSize % PacketSize == 0)
                FUDCnt = iDataSize / PacketSize;
            else
                FUDCnt = iDataSize / PacketSize + 1;
            if(FirmwareKind == 0)
            {
                FirmwareData[0x100] = (BYTE)FUDCnt;
                FirmwareData[0x101] = (BYTE)(FUDCnt >> 8);
            }
            else if(FirmwareKind == 3)
            {
                FirmwareData[0x280] = (BYTE)FUDCnt;
                FirmwareData[0x281] = (BYTE)(FUDCnt >> 8);
            }
            else if(FirmwareKind == 4)
            {}
            else
            {
                FirmwareData[0x200] = (BYTE)FUDCnt;
                FirmwareData[0x201] = (BYTE)(FUDCnt >> 8);
            }
            int HeaderSize = 4;
            BYTE CompData[2048];
            int tempTotSize = 0;
            BYTE *SendFirmwareData = new BYTE[PacketSize * 2 + HeaderSize];
            FormMain->bStopSend = false;
            //AnsiString asDIBDType1 = "";
            //AnsiString asDIBDType2 = "";
            int Addr = 0;
            int asPos = 0;
            int asPos2 = 0;
            char Buf[3];
            AnsiString Data = "";
            FormMain->RetryCnt = 0;
            if(FormMain->FTransfer)
            {
                delete FormMain->FTransfer;
                FormMain->FTransfer = NULL;
            }
            FormMain->FTransfer = new TFormTransfer(this);
            double TotPercent = 0.0;
            double TotalPacketPercent = 0.0;
            FormMain->FTransfer->LabelCount->Caption = "Total : 1 / 1";
            FormMain->FTransfer->LabelTotPercent->Caption = IntToStr((int)TotPercent) + AnsiString("%");
            FormMain->FTransfer->LabelTotalPacket->Caption = IntToStr((int)TotalPacketPercent) + AnsiString("%");
            FormMain->FTransfer->ProgressBar->Position = 0;
            //FormMain->FTransfer->ProgressBar->Smooth = true;
            //FormMain->FTransfer->ProgressBar1->Smooth = true;
            FormMain->FTransfer->LabelName->Caption = ExtractFileName(FormMain->LastFirmwarePathFile);
            //FormMain->FTransfer->AnimateTransfer->Play(0, 31, 0);
            //FormMain->FTransfer->AnimateTransfer->Active = true;
            FormMain->FTransfer->Show();
            FormMain->FTransfer->Update();
            FormMain->FTransfer->BringToFront();
            asMsg = ExtractFileName(FormMain->LastFirmwarePathFile) + " Upload Data";
            FormMain->FTransfer->LabelSign->Visible = false;
            if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
            {
                if(FormMain->ModuleData(FormMain->ComKind, FormMain->Controler1, SendData, sizeof(SendData), 0, FDPI, false))
                {
                    FormMain->AddLog("DABIT Version Request [Receive : OK]" + FormMain->asLogMessage, clBlack);
                    FormMain->NoneDelayCount(500);
                    //FormMain->AddLog("STOP DABIT [OK]" + FormMain->asLogMessage, clBlack);
                    for(int j = 0; j < FUDCnt; j++)
                    {
                        if(FormMain->bStopSend)
                            break;
                        if(FUDCnt - 1 == j)
                        {
                            if(iDataSize % PacketSize == 0)
                                tempSize = PacketSize;
                            else
                                tempSize = iDataSize % PacketSize;
                        }
                        else
                            tempSize = PacketSize;
                        ZeroMemory(SendFirmwareData, PacketSize + HeaderSize);
                        SendFirmwareData[0] = (BYTE)FUDCnt;
                        SendFirmwareData[1] = (BYTE)(FUDCnt >> 8);
                        SendFirmwareData[2] = (BYTE)j;
                        SendFirmwareData[3] = (BYTE)(j >> 8);
                        tempTotSize = tempSize;
                        memcpy(&SendFirmwareData[HeaderSize], &FirmwareData[j * PacketSize], tempTotSize);
                        bConnected = FormMain->ModuleData(FormMain->ComKind, FormMain->Controler1, SendFirmwareData, tempTotSize + HeaderSize, j, FU, false);
                        if(!bConnected)
                        {
                            if(FormMain->bCRCFail || FormMain->bDataFail)
                            {
                                FormMain->AddLog(asMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
                                FormMain->bStopSend = true;
                                break;
                            }
                            else
                            {
                                if(FormMain->RetryCnt < 3 - 1)
                                {
                                    FormMain->RetryCnt++;
                                    FormMain->SchBmpFile = false;
                                    FormMain->AddLog("Fail Transmission Count : " + IntToStr(FormMain->RetryCnt) + FormMain->asLogMessage, clRed);
                                    FormMain->DelayCount(FormMain->LanDelayTime);
                                    j -= 1;
                                    continue;
                                }
                                else
                                {
                                    FormMain->AddLog(asMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
                                    if(!FormMain->bStopSend)// || cbSign->ItemIndex < UserCnt))
                                        FormMain->Message("\r\n" + asMsg + " Transmission failure\r\n", FormMain->LangFile.MessageButton04, "", "", asMsg.Length(), 3, 1, clBlack);
                                    FormMain->bStopSend = true;
                                    break;
                                }
                            }
                        }
                        else
                        {
                            if(FormMain->bStopSend)
                                break;
                            FormMain->AddLog(asMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                            TotalPacketPercent = ((j + 1) * 100 / ((double)FUDCnt));
                            FormMain->FTransfer->ProgressBar1->Position = TotalPacketPercent;
                            FormMain->FTransfer->ProgressBar1->Update();
                            FormMain->FTransfer->LabelTotalPacket->Caption = IntToStr((int)TotalPacketPercent) + AnsiString("%");
                            FormMain->FTransfer->Show();
                            FormMain->FTransfer->Update();
                            FormMain->FTransfer->BringToFront();
                            FormMain->RetryCnt = 0;
                        }
                    }
                }
                else
                    FormMain->AddLog("DABIT Version Request [Receive: FAIL]" + FormMain->asLogMessage, clRed);
            }
            else
            {
                FormMain->AddLog("STOP DABIT [FAIL]" + FormMain->asLogMessage, clRed);
            }
            FormMain->ComClose(FormMain->ComKind);
            if(SendFirmwareData)
            {
                delete [] SendFirmwareData;
                SendFirmwareData = NULL;
            }
            if(FirmwareData)
            {
                delete [] FirmwareData;
                FirmwareData = NULL;
            }
            if(FormMain->FTransfer)
            {
                FormMain->FTransfer->LabelCount->Caption = "Total : 1 / 1";
                TotPercent = (double)100;
                TotalPacketPercent = (double)100;
                FormMain->FTransfer->ProgressBar->Position = TotPercent;
                FormMain->FTransfer->ProgressBar->Update();
                FormMain->FTransfer->ProgressBar1->Position = TotalPacketPercent;
                FormMain->FTransfer->ProgressBar1->Update();
                FormMain->FTransfer->LabelTotPercent->Caption = IntToStr((int)TotPercent) + AnsiString("%");
                FormMain->FTransfer->LabelTotalPacket->Caption = IntToStr((int)TotalPacketPercent) + AnsiString("%");
                FormMain->FTransfer->Show();
                FormMain->FTransfer->Update();
                FormMain->FTransfer->BringToFront();
                FormMain->NoneDelayCount(100);
            }
        }catch(...){
        }
        if(FormMain->FTransfer)
        {
            FormMain->FTransfer->Close();
            delete FormMain->FTransfer;
            FormMain->FTransfer = NULL;
        }
        this->Enabled = true;
        DataTransfer->Enabled = true;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::BitBtnOkClick(TObject *Sender)
{
        if(FileExists(ePath->Text))
            FormMain->LastFirmwarePathFile = ePath->Text;
        Close();
}
//---------------------------------------------------------------------------
                                                                     
void __fastcall TFormFirmware::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormFirmware::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormFirmware::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormFirmware::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

