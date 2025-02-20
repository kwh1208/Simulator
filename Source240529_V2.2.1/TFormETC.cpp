//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormETC.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma link "TntStdCtrls"
#pragma resource "*.dfm"
TFormETC *FormETC;
//---------------------------------------------------------------------------

__fastcall TFormETC::TFormETC(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption           = FormMain->LangFile.FormETC;
        pnCaption->Caption = " " + this->Caption;
        //gbMemInput->Caption     = FormMain->LangFile.FormProtocolLabel21;
        laMemInput->Caption     = FormMain->LangFile.FormProtocolLabel21;
        //gbBGImage->Caption      = FormMain->LangFile.FormProtocolLabel22;
        laBGImage->Caption      = FormMain->LangFile.FormProtocolLabel22;
        //gbMsgErase->Caption     = FormMain->LangFile.BtnErase;
        laMsgErase->Caption     = FormMain->LangFile.BtnErase;
        //gbSync->Caption         = FormMain->LangFile.BtnSignalSend;
        laSync->Caption         = FormMain->LangFile.BtnSignalSend;
        //gbBright->Caption       = FormMain->LangFile.FormProtocolLabel33;
        laBright->Caption       = FormMain->LangFile.FormProtocolLabel33;
        BtnMemInput->Caption       = FormMain->LangFile.BtnSend;
        BtnMsgMemClear->Caption = FormMain->LangFile.BtnSend;
        BtnBGImage->Caption     = FormMain->LangFile.BtnSend;
        BtnSendSync->Caption       = FormMain->LangFile.BtnSend;
        BitBtnPowerLEDOn->Caption  = FormMain->LangFile.BtnPowerLEDOn;
        BitBtnPowerLEDOff->Caption = FormMain->LangFile.BtnPowerLEDOff;
        BtnMsgErase->Caption       = FormMain->LangFile.BtnErase;
        BtnFReset->Caption         = FormMain->LangFile.BtnFReset;
        BtnBright->Caption         = FormMain->LangFile.BtnSend;
        BtnTimeRead->Caption       = FormMain->LangFile.BtnTimeRead;
        BtnTimeSet->Caption        = FormMain->LangFile.BtnSyncLED;
        //Label25->Caption = FormatDateTime("yy-mm-dd '('ddd')'\r\n", Now());
        TDateTime CurTime;
        CurTime = Now();
        Label25->Caption = FormatDateTime("yy-mm-dd '('", Now()) + FormMain->GetDay(FormMain->ReturnDay(CurTime)) + ") ";
        Label25->Caption = Label25->Caption + FormatDateTime("hh:nn:ss", Now());

        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        pnMemInput->Color = FormMain->MainColor;
        laMemInput->Color = FormMain->MainColor;
        laMemInput->Font->Color = FormMain->TextColor;
        laMemInput->Font->Name = FormMain->MainFont;
        laMemInput->Font->Size = FormMain->MainFontSize;
        cbMsgInput->Font->Name = FormMain->MainFont;
        cbMsgInput->Font->Size = FormMain->MainFontSize;
        BtnMemInput->Font->Color = FormMain->TextBGColor;
        BtnMemInput->Font->Name = FormMain->MainFont;
        BtnMemInput->Font->Size = FormMain->MainFontSize;
        pnBtnMemInput->Color = FormMain->ButtonColor;
        gbMemInput->Color = FormMain->MainRectangleColor;

        laMsgErase->Color = FormMain->MainColor;
        pnMsgErase->Color = FormMain->MainColor;
        gbMsgErase->Color = FormMain->MainRectangleColor;
        laMsgErase->Font->Color = FormMain->TextColor;
        laMsgErase->Font->Name = FormMain->MainFont;
        laMsgErase->Font->Size = FormMain->MainFontSize;
        cbMsgMemClear->Font->Name = FormMain->MainFont;
        cbMsgMemClear->Font->Size = FormMain->MainFontSize;
        BtnMsgErase->Font->Color = FormMain->TextBGColor;
        BtnMsgErase->Font->Name = FormMain->MainFont;
        BtnMsgErase->Font->Size = FormMain->MainFontSize;
        pnBtnMsgErase->Color = FormMain->ButtonColor;

        laBGImage->Color = FormMain->MainColor;
        pnBGImage->Color = FormMain->MainColor;
        gbBGImage->Color = FormMain->MainRectangleColor;
        laBGImage->Font->Color = FormMain->TextColor;
        laBGImage->Font->Name = FormMain->MainFont;
        laBGImage->Font->Size = FormMain->MainFontSize;
        cbBGISelect->Font->Name = FormMain->MainFont;
        cbBGISelect->Font->Size = FormMain->MainFontSize;
        BtnBGImage->Font->Color = FormMain->TextBGColor;
        BtnBGImage->Font->Name = FormMain->MainFont;
        BtnBGImage->Font->Size = FormMain->MainFontSize;
        pnBtnBGImage->Color = FormMain->ButtonColor;

        laSync->Color = FormMain->MainColor;
        pnSync->Color = FormMain->MainColor;
        gbSync->Color = FormMain->MainRectangleColor;
        laSync->Font->Color = FormMain->TextColor;
        laSync->Font->Name = FormMain->MainFont;
        laSync->Font->Size = FormMain->MainFontSize;
        cbSoundSec1->Font->Name = FormMain->MainFont;
        cbSoundSec1->Font->Size = FormMain->MainFontSize;
        cbSoundSec2->Font->Name = FormMain->MainFont;
        cbSoundSec2->Font->Size = FormMain->MainFontSize;
        SLabel1->Font->Color = FormMain->TextColor;
        SLabel1->Font->Name = FormMain->MainFont;
        SLabel1->Font->Size = FormMain->MainFontSize;
        SLabel2->Font->Color = FormMain->TextColor;
        SLabel2->Font->Name = FormMain->MainFont;
        SLabel2->Font->Size = FormMain->MainFontSize;
        BtnSendSync->Font->Color = FormMain->TextBGColor;
        BtnSendSync->Font->Name = FormMain->MainFont;
        BtnSendSync->Font->Size = FormMain->MainFontSize;
        pnBtnSendSync->Color = FormMain->ButtonColor;

        laBright->Color = FormMain->MainColor;
        pnBright->Color = FormMain->MainColor;
        gbBright->Color = FormMain->MainRectangleColor;
        laBright->Font->Color = FormMain->TextColor;
        laBright->Font->Name = FormMain->MainFont;
        laBright->Font->Size = FormMain->MainFontSize;
        cbBright->Font->Name = FormMain->MainFont;
        cbBright->Font->Size = FormMain->MainFontSize;
        BtnBright->Font->Color = FormMain->TextBGColor;
        BtnBright->Font->Name = FormMain->MainFont;
        BtnBright->Font->Size = FormMain->MainFontSize;
        pnBtnBright->Color = FormMain->ButtonColor;

        BitBtnPowerLEDOn->Font->Color = FormMain->TextBGColor;
        BitBtnPowerLEDOn->Font->Name = FormMain->MainFont;
        BitBtnPowerLEDOn->Font->Size = FormMain->MainFontSize;
        pnBitBtnPowerLEDOn->Color = FormMain->ButtonColor;
        BitBtnPowerLEDOff->Font->Color = FormMain->TextBGColor;
        BitBtnPowerLEDOff->Font->Name = FormMain->MainFont;
        BitBtnPowerLEDOff->Font->Size = FormMain->MainFontSize;
        pnBitBtnPowerLEDOff->Color = FormMain->ButtonColor;
        Label25->Color = FormMain->TextBGColor;
        Label25->Font->Color = (TColor)RGB(245, 245, 245);
        Label25->Font->Name = FormMain->MainFont;
        //Label25->Font->Size = FormMain->MainFontSize;
        BtnTimeRead->Font->Color = FormMain->TextBGColor;
        BtnTimeRead->Font->Name = FormMain->MainFont;
        BtnTimeRead->Font->Size = FormMain->MainFontSize;
        pnBtnTimeRead->Color = FormMain->ButtonColor;
        BtnTimeSet->Font->Color = FormMain->TextBGColor;
        BtnTimeSet->Font->Name = FormMain->MainFont;
        BtnTimeSet->Font->Size = FormMain->MainFontSize;
        pnBtnTimeSet->Color = FormMain->ButtonColor;
        BtnFReset->Font->Color = FormMain->TextBGColor;
        BtnFReset->Font->Name = FormMain->MainFont;
        BtnFReset->Font->Size = FormMain->MainFontSize;
        pnBtnFReset->Color = FormMain->ButtonColor;
        BtnMsgMemClear->Font->Color = FormMain->TextBGColor;
        BtnMsgMemClear->Font->Name = FormMain->MainFont;
        BtnMsgMemClear->Font->Size = FormMain->MainFontSize;
        pnBtnMsgMemClear->Color = FormMain->ButtonColor;
        BtnOk->Font->Color = FormMain->TextBGColor;
        BtnOk->Font->Name = FormMain->MainFont;
        BtnOk->Font->Size = FormMain->MainFontSize;
        pnBtnOk->Color = FormMain->ButtonColor;
        TntLabel8->Font->Color = FormMain->TextColor;
        TntLabel8->Font->Name = FormMain->MainFont;
        TntLabel8->Font->Size = FormMain->MainFontSize;
        TntLabel9->Font->Color = FormMain->TextColor;
        TntLabel9->Font->Name = FormMain->MainFont;
        TntLabel9->Font->Size = FormMain->MainFontSize;
        cbWaitTime->Font->Name = FormMain->MainFont;
        cbWaitTime->Font->Size = FormMain->MainFontSize;
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnMsgMemClearClick(TObject *Sender)
{
        FormMain->bStopSend = false;
        int Addr = 0;
        AnsiString Data = "";
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        BYTE SendData[11];
        SendData[0] = 0x00;
        BYTE tempOpCode = 0;
        int iSendSize = 1;
        AnsiString asMessage = "";
        // 일반문구 전체 삭제
        if(Sender == BtnBGImage)
        {
            SendData[0] = cbBGISelect->ItemIndex;
            tempOpCode = EDI;
            asMessage = FormMain->LangFile.Message10;
        }
        else
        {
            if(cbMsgMemClear->ItemIndex == 0)
                SendData[0] = 0x80;
            else
                SendData[0] = cbMsgMemClear->ItemIndex - 1;
            tempOpCode = FMMC;
            asMessage = FormMain->LangFile.Message17;
        }
        bool Connect = false;
        ButtonEnabled(false);
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(FormMain->PorotocolType)
            {
                char Buf[4];
                ZeroMemory(Buf, sizeof(Buf));
                AnsiString asSendData = "";
                if(Sender == BtnBGImage)
                {
                    asSendData = "![" + FormMain->IntTo485Address(Addr) + "020";
                    wsprintf(Buf, "%03d", SendData[0]);
                }
                else
                {
                    if(cbMsgMemClear->ItemIndex == 0)
                        SendData[0] = 99;
                    else
                        SendData[0] = cbMsgMemClear->ItemIndex - 1;
                    asSendData = "![" + FormMain->IntTo485Address(Addr) + "061";
                    wsprintf(Buf, "%02d", SendData[0]);
                }
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                ZeroMemory(SendData, sizeof(SendData));
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(asMessage + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
            }
            else
            {
                Connect = FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, (OPCode)tempOpCode, FormMain->bMsg);
                if(Connect)
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(asMessage + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnMsgEraseClick(TObject *Sender)
{
        AnsiString asData = "";
        char Buf[3];
        char Buf2[3];
        for(int i = 0; i < 11; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                FormMain->ProtocolData.ProtocolDispControl[i][j] = 1;
                FormMain->ProtocolData.ProtocolDispType[i][j]  = 1;
                FormMain->ProtocolData.ProtocolCodeKind[i][j]  = 0;
                FormMain->ProtocolData.ProtocolFontSize[i][j]  = 0;
                asData = "효과없음";
                ZeroMemory(&FormMain->ProtocolData.ProtocolEntEffect[i][j], sizeof(FormMain->ProtocolData.ProtocolEntEffect[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolEntEffect[i][j][0], asData.c_str(), asData.Length());
                ZeroMemory(&FormMain->ProtocolData.ProtocolLasDirect[i][j], sizeof(FormMain->ProtocolData.ProtocolLasDirect[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                asData = "방향 없음";
                ZeroMemory(&FormMain->ProtocolData.ProtocolEntDirect[i][j], sizeof(FormMain->ProtocolData.ProtocolEntDirect[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolEntDirect[i][j][0], asData.c_str(), asData.Length());
                ZeroMemory(&FormMain->ProtocolData.ProtocolLasDirect[i][j], sizeof(FormMain->ProtocolData.ProtocolLasDirect[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                asData = "20";
                ZeroMemory(&FormMain->ProtocolData.ProtocolSpeed[i][j], sizeof(FormMain->ProtocolData.ProtocolSpeed[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolSpeed[i][j][0], asData.c_str(), asData.Length());
                asData = "2";
                ZeroMemory(&FormMain->ProtocolData.ProtocolDelay[i][j], sizeof(FormMain->ProtocolData.ProtocolDelay[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolDelay[i][j][0], asData.c_str(), asData.Length());
                FormMain->ProtocolData.ProtocolStartXPos[i][j] = 0;
                FormMain->ProtocolData.ProtocolStartYPos[i][j] = 0;
                FormMain->ProtocolData.ProtocolEndXPos[i][j]   = 0;
                FormMain->ProtocolData.ProtocolEndYPos[i][j]   = 0;
                FormMain->ProtocolData.ProtocolBGImage[i][j]   = 0;
                FormMain->ProtocolData.ProtocolColorType[i][j] = 0;
                asData = "1";
                ZeroMemory(&FormMain->ProtocolData.ProtocolColor[i][j], sizeof(FormMain->ProtocolData.ProtocolColor[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolColor[i][j][0], asData.c_str(), asData.Length());
                asData = "0";
                ZeroMemory(&FormMain->ProtocolData.ProtocolBGColor[i][j], sizeof(FormMain->ProtocolData.ProtocolBGColor[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolBGColor[i][j][0], asData.c_str(), asData.Length());
                if(i == 0)
                    asData = "RealTime Message 0-" + IntToStr(j);
                else
                    asData = "Page Message " + IntToStr(i - 1) + "-" + IntToStr(j);
                ZeroMemory(&FormMain->ProtocolData.ProtocolData[i][j], sizeof(FormMain->ProtocolData.ProtocolData[0][0]));
                memcpy(&FormMain->ProtocolData.ProtocolData[i][j][0], asData.c_str(), asData.Length());
                AnsiString EditFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath)
                    + "EnvData" + IntToStr(i) + IntToStr(j) + ".dat";
                if(FileExists(EditFileName))
                    DeleteFile(EditFileName);
            }
        }
        FormMain->rbTextNoRoadClick(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::FormCreate(TObject *Sender)
{
        cbBGISelect->Clear();
        cbBGISelect->Items->Add(FormMain->LangFile.FormProtocolEffect1);
        for(int i = 1; i <= 255; i++)
            cbBGISelect->Items->Add(IntToStr(i));
        cbBGISelect->ItemIndex = 0;

        int PageIndex = FormMain->ProtocolPageNo;
        cbMsgInput->Clear();
        cbMsgMemClear->Clear();
        cbMsgMemClear->Items->Add(FormMain->LangFile.FormProtocolEffect9);
        for(int i = 0; i < 10; i++)
            cbMsgInput->Items->Add(IntToStr(i + 1));
        for(int i = 0; i < FormMain->MessageCount; i++)
            cbMsgMemClear->Items->Add("Page " + IntToStr(i + 1));

        cbMsgInput->ItemIndex = FormMain->MessageCount - 1;
        if(cbMsgInput->ItemIndex <= 0)
            cbMsgInput->ItemIndex = 0;
        FormMain->ProtocolPageNo = PageIndex;
        cbMsgMemClear->ItemIndex = 0;
        cbSoundSec1->ItemIndex = 0;
        cbSoundSec2->ItemIndex = 0;
        cbBright->ItemIndex = 0;
        cbWaitTime->ItemIndex = (FormMain->WaitTime / 1000) - 1;
        if(cbWaitTime->ItemIndex < 0)
            cbWaitTime->ItemIndex = 3;
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnSendSyncClick(TObject *Sender)
{
        // 신호 출력 전송 버튼
        FormMain->bStopSend = false;
        BYTE SendData[18];
        int iSendSize = 4;
        ZeroMemory(SendData, sizeof(SendData));
        try{
            switch(cbSoundSec1->ItemIndex)
            {
                case 0:
                {
                    SendData[0] = 0x00;
                    SendData[1] = 0xf1;
                }
                break;
                case 1:
                {
                    SendData[0] = 0x00;
                    SendData[1] = 0xf0;
                }
                break;
                case 2:
                {
                    SendData[0] = 0x00;
                    SendData[1] = 0x00;
                }
                break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    SendData[0] = cbSoundSec1->ItemIndex - 2;
                break;
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    SendData[0] = 12 + (cbSoundSec1->ItemIndex - 13) * 2;
                break;
                case 18:
                    SendData[0] = 25;
                break;
                case 19:
                    SendData[0] = 30;
                break;
                case 20:
                    SendData[0] = 40;
                break;
                case 21:
                    SendData[0] = 50;
                break;
                case 22:
                    SendData[0] = 75;
                break;
                case 23:
                    SendData[0] = 100;
                break;
                case 24:
                    SendData[0] = 120;
                break;
                case 25:
                    SendData[0] = 140;
                break;
                case 26:
                    SendData[0] = 160;
                break;
                case 27:
                    SendData[0] = 180;
                break;
                case 28:
                    SendData[0] = 200;
                break;
                case 29:
                    SendData[0] = 220;
                break;
                case 30:
                    SendData[0] = 230;
                break;
                default:
                    SendData[0] = 1;
                break;
            }
            switch(cbSoundSec2->ItemIndex)
            {
                case 0:
                {
                    SendData[2] = 0x00;
                    SendData[3] = 0xf1;
                }
                break;
                case 1:
                {
                    SendData[2] = 0x00;
                    SendData[3] = 0xf0;
                }
                break;
                case 2:
                {
                    SendData[2] = 0x00;
                    SendData[3] = 0x00;
                }
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    SendData[2] = cbSoundSec2->ItemIndex - 2;
                break;
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    SendData[2] = 12 + (cbSoundSec2->ItemIndex - 13) * 2;
                break;
                case 18:
                    SendData[2] = 25;
                break;
                case 19:
                    SendData[2] = 30;
                break;
                case 20:
                    SendData[2] = 40;
                break;
                case 21:
                    SendData[2] = 50;
                break;
                case 22:
                    SendData[2] = 75;
                break;
                case 23:
                    SendData[2] = 100;
                break;
                case 24:
                    SendData[2] = 120;
                break;
                case 25:
                    SendData[2] = 140;
                break;
                case 26:
                    SendData[2] = 160;
                break;
                case 27:
                    SendData[2] = 180;
                break;
                case 28:
                    SendData[2] = 200;
                break;
                case 29:
                    SendData[2] = 220;
                break;
                case 30:
                    SendData[2] = 230;
                break;
                default:
                    SendData[2] = 1;
                break;
            }
        }catch(...){
            SendData[0] = 1;
            SendData[2] = 1;
        }
        ButtonEnabled(false);
        int Addr = 0;
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        try{
            if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
            {
                if(FormMain->PorotocolType)
                {
                    AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "022";
                    char Buf[6];
                    ZeroMemory(Buf, sizeof(Buf));
                    if(SendData[1] == 0xf1)
                        asSendData = asSendData + "61696";
                    else if(SendData[1] == 0xf0)
                        asSendData = asSendData + "61440";
                    else
                    {
                        wsprintf(Buf, "%05d", SendData[0]);
                        asSendData = asSendData + AnsiString(Buf);
                    }
                    ZeroMemory(Buf, sizeof(Buf));
                    if(SendData[3] == 0xf1)
                        asSendData = asSendData + "61696!]";
                    else if(SendData[3] == 0xf0)
                        asSendData = asSendData + "61440!]";
                    else
                    {
                        wsprintf(Buf, "%05d", SendData[2]);
                        asSendData = asSendData + AnsiString(Buf) + "!]";
                    }
                    iSendSize = asSendData.Length();
                    ZeroMemory(SendData, sizeof(SendData));
                    memcpy(SendData, asSendData.c_str(), iSendSize);
                    if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, FormMain->bMsg))
                    {
                        if(FormMain->bDataFail)
                            FormMain->AddLog(FormMain->LangFile.BtnSignalSend + " [Fail] " + FormMain->asLogMessage, clRed);
                        else
                            FormMain->AddLog(FormMain->LangFile.BtnSignalSend + " [OK] " + FormMain->asLogMessage, clBlack);
                    }
                    else
                        FormMain->AddLog(FormMain->LangFile.BtnSignalSend + " [Fail] " + FormMain->asLogMessage, clRed);
                }
                else
                {
                    if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, SO, FormMain->bMsg))
                    {
                        if(FormMain->bDataFail)
                            FormMain->AddLog(FormMain->LangFile.BtnSignalSend + " [Fail] " + FormMain->asLogMessage, clRed);
                        else
                            FormMain->AddLog(FormMain->LangFile.BtnSignalSend + " [OK] " + FormMain->asLogMessage, clBlack);
                    }
                    else
                        FormMain->AddLog(FormMain->LangFile.BtnSignalSend + " [Fail] " + FormMain->asLogMessage, clRed);
                }
                FormMain->ComClose(FormMain->ComKind);
            }
        }catch(...){
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnFResetClick(TObject *Sender)
{
        // 전광판 공장 초기화 버튼
        BYTE SendData[8];
        ZeroMemory(SendData, sizeof(SendData));
        SendData[0] = FormMain->ProgramType;
        SendData[1] = FormMain->ModuleHeight;
        SendData[2] = FormMain->ModuleWidth;
        int iSendSize = 3;
        ButtonEnabled(false);
        int Addr = 0;
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(FormMain->PorotocolType)
            {
                AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "042!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(FormMain->LangFile.BtnFReset + " (" + IntToStr(FormMain->ModuleHeight) + " X " + IntToStr(FormMain->ModuleWidth) + ") [FAIL]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(FormMain->LangFile.BtnFReset + " (" + IntToStr(FormMain->ModuleHeight) + " X " + IntToStr(FormMain->ModuleWidth) + ") [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(FormMain->LangFile.BtnFReset + " (" + IntToStr(FormMain->ModuleHeight) + " X " + IntToStr(FormMain->ModuleWidth) + ") [FAIL]" + FormMain->asLogMessage, clRed);
            }
            else
            {
                if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, DFS, FormMain->bMsg))
                    FormMain->AddLog(FormMain->LangFile.BtnFReset + " (" + IntToStr(FormMain->ModuleHeight) + " X " + IntToStr(FormMain->ModuleWidth) + ") [OK]" + FormMain->asLogMessage, clBlack);
                else
                    FormMain->AddLog(FormMain->LangFile.BtnFReset + " (" + IntToStr(FormMain->ModuleHeight) + " X " + IntToStr(FormMain->ModuleWidth) + ") [FAIL]" + FormMain->asLogMessage, clRed);
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnBrightClick(TObject *Sender)
{
        // 전광판 밝기 전송 버튼
        BYTE SendData[10];
        ZeroMemory(SendData, sizeof(SendData));
        int BValue = 100;
        if(cbBright->ItemIndex == 1)
            BValue = 75;
        else if(cbBright->ItemIndex == 2)
            BValue = 50;
        else if(cbBright->ItemIndex == 3)
            BValue = 25;
        else if(cbBright->ItemIndex == 4)
            BValue = 5;
        try{
            SendData[0] = BValue;
        }catch(...){
        }
        int iSendSize = 1;
        ButtonEnabled(false);
        int Addr = 0;
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(FormMain->PorotocolType)
            {
                AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "050";
                if(cbBright->ItemIndex == 1)
                    asSendData = asSendData + "75!]";
                else if(cbBright->ItemIndex == 2)
                    asSendData = asSendData + "50!]";
                else if(cbBright->ItemIndex == 3)
                    asSendData = asSendData + "25!]";
                else if(cbBright->ItemIndex == 4)
                    asSendData = asSendData + "05!]";
                else
                    asSendData = asSendData + "99!]";
                iSendSize = asSendData.Length();
                ZeroMemory(SendData, sizeof(SendData));
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(FormMain->LangFile.FormProtocolLabel33 +  "[FAIL]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(FormMain->LangFile.FormProtocolLabel33 +  "[OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(FormMain->LangFile.FormProtocolLabel33 +  "[FAIL]" + FormMain->asLogMessage, clRed);
            }
            else
            {
                if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, BC, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(FormMain->LangFile.FormProtocolLabel33 +  "[FAIL]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(FormMain->LangFile.FormProtocolLabel33 +  "[OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(FormMain->LangFile.FormProtocolLabel33 +  "[FAIL]" + FormMain->asLogMessage, clRed);
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::ButtonEnabled(bool bEnabled)
{
        // 버튼 활성화 함수
        BitBtnPowerLEDOn->Enabled = bEnabled;
        BitBtnPowerLEDOff->Enabled = bEnabled;
        BtnSendSync->Enabled = bEnabled;
        BtnMemInput->Enabled = bEnabled;
        BtnMsgMemClear->Enabled = bEnabled;
        BtnBGImage->Enabled = bEnabled;
        BtnFReset->Enabled = bEnabled;
        BtnBright->Enabled = bEnabled;
        BtnTimeRead->Enabled = bEnabled;
        BtnTimeSet->Enabled = bEnabled;
        BtnMsgErase->Enabled = bEnabled;
        this->Enabled = bEnabled;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnMemInputClick(TObject *Sender)
{
        FormMain->bStopSend = false;
        int Addr = 0;
        AnsiString Data = "";
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        BYTE SendData[10];
        SendData[0] = 0x00;
        BYTE tempOpCode = 0;
        int iSendSize = 1;
        AnsiString asMessage = "";
        // 일반문구 갯수 등록
        SendData[0] = cbMsgInput->ItemIndex + 1;
        FormMain->MessageCount = SendData[0];
        tempOpCode = FMI;
        asMessage = FormMain->LangFile.Message09;
        bool Connect = false;
        ButtonEnabled(false);
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(FormMain->PorotocolType)
            {
                AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "060";
                char Buf[3];
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%02d", SendData[0]);
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                ZeroMemory(SendData, sizeof(SendData));
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(asMessage + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
            }
            else
            {
                Connect = FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, (OPCode)tempOpCode, FormMain->bMsg);
                if(Connect)
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(asMessage + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        if(FormMain->MessageCount != cbMsgInput->ItemIndex + 1) // 일반문구 갯수 이외의 구조체 초기화
        {
            char Buf[3];
            char Buf2[3];
            AnsiString asFileName = "";
            AnsiString asData = "";
            for(int i = 1; i <= FormMain->MessageCount; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(j == 0)
                        FormMain->ProtocolData.ProtocolDispControl[i][j] = 1;
                    else
                        FormMain->ProtocolData.ProtocolDispControl[i][j] = 0;
                    FormMain->ProtocolData.ProtocolDispType[i][j]  = 0;
                    FormMain->ProtocolData.ProtocolCodeKind[i][j]  = 0;
                    FormMain->ProtocolData.ProtocolFontSize[i][j]  = 0;
                    asData = "효과없음";
                    memcpy(&FormMain->ProtocolData.ProtocolEntEffect[i][j][0], asData.c_str(), asData.Length());
                    memcpy(&FormMain->ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                    asData = "방향 없음";
                    memcpy(&FormMain->ProtocolData.ProtocolEntDirect[i][j][0], asData.c_str(), asData.Length());
                    memcpy(&FormMain->ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                    FormMain->ProtocolData.ProtocolStartXPos[i][j] = 0;
                    FormMain->ProtocolData.ProtocolStartYPos[i][j] = 0;
                    FormMain->ProtocolData.ProtocolEndXPos[i][j] = 0;
                    FormMain->ProtocolData.ProtocolEndYPos[i][j] = 0;
                    FormMain->ProtocolData.ProtocolBGImage[i][j] = 0;
                    FormMain->ProtocolData.ProtocolColorType[i][j] = 0;
                    asData = "20";
                    memcpy(&FormMain->ProtocolData.ProtocolSpeed[i][j][0], asData.c_str(), asData.Length());
                    asData = "2";
                    memcpy(&FormMain->ProtocolData.ProtocolDelay[i][j][0], asData.c_str(), asData.Length());
                    asData = "1";
                    memcpy(&FormMain->ProtocolData.ProtocolColor[i][j][0], asData.c_str(), asData.Length());
                    asData = "0";
                    memcpy(&FormMain->ProtocolData.ProtocolBGColor[i][j][0], asData.c_str(), asData.Length());
                    ZeroMemory(Buf, 3);
                    wsprintf(Buf, "%02d", i + 1);
                    ZeroMemory(Buf2, 3);
                    wsprintf(Buf2, "%02d", j + 1);
                    asData = FormMain->LangFile.FormProtocolTab2 + AnsiString(Buf).TrimRight() + "-" + AnsiString(Buf2).TrimRight();
                    memcpy(&FormMain->ProtocolData.ProtocolData[i][j][0], asData.c_str(), asData.Length());
                    asFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "EnvData" + IntToStr(i) + IntToStr(j) + ".dat";
                    if(FileExists(asFileName))
                        DeleteFile(asFileName);
                }
            }
        }
        FormMain->MessageCount = cbMsgInput->ItemIndex + 1;
        int PageIndex = FormMain->ProtocolPageNo;
        int MsgMemClearIndex = cbMsgMemClear->ItemIndex;
        FormMain->cbPageNo->Clear();
        cbMsgMemClear->Clear();
        cbMsgMemClear->Items->Add(FormMain->LangFile.FormProtocolEffect9);
        for(int i = 0; i < FormMain->MessageCount; i++)
        {
            FormMain->cbPageNo->Items->Add(IntToStr(i + 1));
            cbMsgMemClear->Items->Add("Page " + IntToStr(i + 1));
        }
        if(FormMain->MessageCount - 1 < PageIndex)
            FormMain->cbPageNo->ItemIndex = FormMain->MessageCount - 1;
        else
            FormMain->cbPageNo->ItemIndex = PageIndex;
        if(FormMain->MessageCount < MsgMemClearIndex)
            cbMsgMemClear->ItemIndex = FormMain->MessageCount;
        else
            cbMsgMemClear->ItemIndex = MsgMemClearIndex;
        FormMain->ProtocolPageNo = FormMain->cbPageNo->ItemIndex;
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BitBtnPowerLEDClick(TObject *Sender)
{
        //전광판 전원 전송 버튼
        FormMain->bStopSend = false;
        int Addr = 0;
        ButtonEnabled(false);
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        OPCode tempOPC;
        BYTE SendData[9];
        int iSendSize = 1;
        ZeroMemory(SendData, sizeof(SendData));
        if(Sender == BitBtnPowerLEDOff)
            SendData[0] = PowerOff;
        else
            SendData[0] = PowerOn;
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(FormMain->PorotocolType)
            {
                AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "021";
                char Buf[3];
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%01d", SendData[0]);
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            FormMain->AddLog(FormMain->LangFile.Message04 + " [Fail]" + FormMain->asLogMessage, clRed);
                        else
                            FormMain->AddLog(FormMain->LangFile.Message03 + " [Fail]" + FormMain->asLogMessage, clRed);
                    }
                    else
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            FormMain->AddLog(FormMain->LangFile.Message04 + " [OK]" + FormMain->asLogMessage, clBlack);
                        else
                            FormMain->AddLog(FormMain->LangFile.Message03 + " [OK]" + FormMain->asLogMessage, clBlack);
                    }
                }
                else
                {
                    if(Sender == BitBtnPowerLEDOff)
                        FormMain->AddLog(FormMain->LangFile.Message04 + " [Fail]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(FormMain->LangFile.Message03 + " [Fail]" + FormMain->asLogMessage, clRed);
                }
            }
            else
            {
                if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, PC, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            FormMain->AddLog(FormMain->LangFile.Message04 + " [Fail]" + FormMain->asLogMessage, clRed);
                        else
                            FormMain->AddLog(FormMain->LangFile.Message03 + " [Fail]" + FormMain->asLogMessage, clRed);
                    }
                    else
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            FormMain->AddLog(FormMain->LangFile.Message04 + " [OK]" + FormMain->asLogMessage, clBlack);
                        else
                            FormMain->AddLog(FormMain->LangFile.Message03 + " [OK]" + FormMain->asLogMessage, clBlack);
                    }
                }
                else
                {
                    if(Sender == BitBtnPowerLEDOff)
                        FormMain->AddLog(FormMain->LangFile.Message04 + " [Fail]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(FormMain->LangFile.Message03 + " [Fail]" + FormMain->asLogMessage, clRed);
                }
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnOkClick(TObject *Sender)
{
        FormMain->WaitTime = StrToIntDef(cbWaitTime->Text, 4) * 1000;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnTimeSetClick(TObject *Sender)
{
        ButtonEnabled(false);
        FormMain->CurrenTimeSync();
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::BtnTimeReadClick(TObject *Sender)
{
        //시간읽기 전송 버튼
        FormMain->bStopSend = false;
        int Addr = 0;
        ButtonEnabled(false);
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        BYTE SendData[8];
        int iSendSize = 1;
        ZeroMemory(SendData, sizeof(SendData));
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(FormMain->PorotocolType)
            {
                AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "031!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(FormMain->LangFile.BtnTimeRead + " [Fail]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(FormMain->LangFile.BtnTimeRead + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(FormMain->LangFile.BtnTimeRead + " [Fail]" + FormMain->asLogMessage, clRed);
            }
            else
            {
                if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, 0, 0, RCT, FormMain->bMsg))
                {
                    if(FormMain->bDataFail)
                        FormMain->AddLog(FormMain->LangFile.BtnTimeRead + " [Fail]" + FormMain->asLogMessage, clRed);
                    else
                        FormMain->AddLog(FormMain->LangFile.BtnTimeRead + " [OK]" + FormMain->asLogMessage, clBlack);
                }
                else
                    FormMain->AddLog(FormMain->LangFile.BtnTimeRead + " [Fail]" + FormMain->asLogMessage, clRed);
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::pnCaptionMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            OldXPos = X;
            OldYPos = Y;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::pnCaptionMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormETC::cbWaitTimeClick(TObject *Sender)
{
        FormMain->WaitTime = StrToIntDef(cbWaitTime->Text, 4) * 1000;
}
//---------------------------------------------------------------------------

