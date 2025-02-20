//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormSetting.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "TntButtons"
#pragma link "TntComCtrls"
#pragma link "TntStdCtrls"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormSetting *FormSetting;
//---------------------------------------------------------------------------
__fastcall TFormSetting::TFormSetting(TComponent* Owner)
        : TForm(Owner)
{
        pnCaption->Caption = " " + this->Caption;
        TntLabel1->Caption        = FormMain->LangFile.FormProtocolLabel30;
        TntLabel2->Caption        = FormMain->LangFile.FormProtocolLabel31;
        TntLabel3->Caption        = FormMain->LangFile.FormProtocolLabel32;
        TntLabel10->Caption       = FormMain->LangFile.FormProtocolLabel34;
        //gbSignSize->Caption       = FormMain->LangFile.BtnSize;
        laSignSize->Caption       = FormMain->LangFile.BtnSize;
        //laProtocol->Caption       = gbSignSize->Caption;
        BtnSignSize->Caption      = FormMain->LangFile.BtnSend;
        BtnFontSend->Caption      = FormMain->LangFile.BtnFontSend;
        BtnLEDSetting->Caption    = FormMain->LangFile.BtnLEDSetting;
        BtnDIBDDownload->Caption  = FormMain->LangFile.BtnDIBDDownload;
        BtnETC->Caption           = FormMain->LangFile.BtnETC;
        BtnLog->Caption           = FormMain->LangFile.BtnLog;
        BtnComPort->Caption       = FormMain->LangFile.BtnPort;
        BtnOk->Caption            = FormMain->LangFile.MessageButton10;
        BtnApply->Caption         = FormMain->LangFile.BtnApply;

        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        gbSignSize->Color = FormMain->MainRectangleColor;
        pnSignSize->Color = FormMain->MainColor;
        gbProtocol->Color = FormMain->MainRectangleColor;
        pnProtocol->Color = FormMain->MainColor;
        rgASCII->Color = FormMain->MainColor;
        rgHex->Color = FormMain->MainColor;
        laSignSize->Color = FormMain->MainColor;
        laSignSize->Font->Color = FormMain->TextColor;
        laSignSize->Font->Name = FormMain->MainFont;
        laSignSize->Font->Size = FormMain->MainFontSize;
        laProtocol->Color = FormMain->MainColor;
        laProtocol->Font->Color = FormMain->TextColor;
        laProtocol->Font->Name = FormMain->MainFont;
        laProtocol->Font->Size = FormMain->MainFontSize;
        rgASCII->Font->Color = FormMain->TextColor;
        rgASCII->Font->Name = FormMain->MainFont;
        rgASCII->Font->Size = FormMain->MainFontSize;
        rgHex->Font->Color = FormMain->TextColor;
        rgHex->Font->Name = FormMain->MainFont;
        rgHex->Font->Size = FormMain->MainFontSize;
        TntEdit1->Font->Name = FormMain->MainFont;
        TntEdit1->Font->Size = FormMain->MainFontSize;
        TntEdit2->Font->Name = FormMain->MainFont;
        TntEdit2->Font->Size = FormMain->MainFontSize;
        cbProgramType->Font->Name = FormMain->MainFont;
        cbProgramType->Font->Size = FormMain->MainFontSize;
        cbDirection->Font->Name = FormMain->MainFont;
        cbDirection->Font->Size = FormMain->MainFontSize;

        TTntLabel *Label;
        for(int i = 0; i < 11; i++)
        {
            Label = dynamic_cast<TTntLabel*>(FindComponent("TntLabel" + IntToStr(i + 1)));
            if(Label)
            {
                Label->Font->Color = FormMain->TextColor;
                Label->Font->Name = FormMain->MainFont;
                Label->Font->Size = FormMain->MainFontSize;
            }
        }
        BtnSignSize->Font->Color = FormMain->TextBGColor;
        BtnSignSize->Font->Name = FormMain->MainFont;
        BtnSignSize->Font->Size = FormMain->MainFontSize;
        pnBtnSignSize->Color = FormMain->ButtonColor;
        BtnFontSend->Font->Color = FormMain->TextBGColor;
        BtnFontSend->Font->Name = FormMain->MainFont;
        BtnFontSend->Font->Size = FormMain->MainFontSize;
        pnBtnFontSend->Color = FormMain->ButtonColor;
        BtnLEDSetting->Font->Color = FormMain->TextBGColor;
        BtnLEDSetting->Font->Name = FormMain->MainFont;
        BtnLEDSetting->Font->Size = FormMain->MainFontSize;
        pnBtnLEDSetting->Color = FormMain->ButtonColor;
        BtnDIBDDownload->Font->Color = FormMain->TextBGColor;
        BtnDIBDDownload->Font->Name = FormMain->MainFont;
        BtnDIBDDownload->Font->Size = FormMain->MainFontSize;
        pnBtnDIBDDownload->Color = FormMain->ButtonColor;
        BtnETC->Font->Color = FormMain->TextBGColor;
        BtnETC->Font->Name = FormMain->MainFont;
        BtnETC->Font->Size = FormMain->MainFontSize;
        pnBtnETC->Color = FormMain->ButtonColor;
        BtnLog->Font->Color = FormMain->TextBGColor;
        BtnLog->Font->Name = FormMain->MainFont;
        BtnLog->Font->Size = FormMain->MainFontSize;
        pnBtnLog->Color = FormMain->ButtonColor;
        BtnComPort->Font->Color = FormMain->TextBGColor;
        BtnComPort->Font->Name = FormMain->MainFont;
        BtnComPort->Font->Size = FormMain->MainFontSize;
        pnBtnComPort->Color = FormMain->ButtonColor;
        BtnOk->Font->Color = FormMain->TextBGColor;
        BtnOk->Font->Name = FormMain->MainFont;
        BtnOk->Font->Size = FormMain->MainFontSize;
        pnBtnOk->Color = FormMain->ButtonColor;
        BtnApply->Font->Color = FormMain->TextBGColor;
        BtnApply->Font->Name = FormMain->MainFont;
        BtnApply->Font->Size = FormMain->MainFontSize;
        pnBtnApply->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::FormCreate(TObject *Sender)
{
        UpDownHeight->Position = FormMain->ModuleHeight;
        UpDownWidth->Position = FormMain->ModuleWidth;
        if(FormMain->ProgramType == e2BPP)
            cbProgramType->ItemIndex = 0;
        else if(FormMain->ProgramType == e8BPP)
            cbProgramType->ItemIndex = 2;
        //else if(FormMain->ProgramType == e16BPP)
        //    cbProgramType->ItemIndex = 3;
        else if(FormMain->ProgramType == e24BPP)
            cbProgramType->ItemIndex = 3;
        else
            cbProgramType->ItemIndex = 1;
        cbDirection->Clear();
        cbDirection->Items->Add(FormMain->LangFile.FormProtocolDirectItem1);
        cbDirection->Items->Add(FormMain->LangFile.FormProtocolDirectItem2);
        cbDirection->Items->Add(FormMain->LangFile.FormProtocolDirectItem3);
        cbDirection->Items->Add(FormMain->LangFile.FormProtocolDirectItem4);
        cbDirection->Items->Add(FormMain->LangFile.FormProtocolDirectItem5);
        cbDirection->ItemIndex = FormMain->Direct;
        cbDirectCheck->Checked = FormMain->DirectCheck;
        cbDirectCheckClick(this);
        if(FormMain->PorotocolType)
            rgASCII->Checked = true;
        else
            rgHex->Checked = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnApplyClick(TObject *Sender)
{
        this->Tag = 1;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnOkClick(TObject *Sender)
{
        this->Tag = 0;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnFontSendClick(TObject *Sender)
{
        // 폰트 전송 창 열기 버튼
        FFont = new TFormFont(this);
        FFont->ShowModal();
        FormMain->SaveIniFile();
        if(FFont)
        {
            delete FFont;
            FFont = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnLEDSettingClick(TObject *Sender)
{
        // LED 모듈 설정 셋팅 창 열기 버튼
        FLEDSet = new TFormLEDSet(this);
        FLEDSet->ShowModal();
        FormMain->SaveIniFile();
        if(FLEDSet)
        {
            delete FLEDSet;
            FLEDSet = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnDIBDDownloadClick(TObject *Sender)
{
        // 펌웨어 전송창 열기 버튼
        FFirmware = new TFormFirmware(this);
        FFirmware->ShowModal();
        FormMain->SaveIniFile();
        if(FFirmware)
        {
            delete FFirmware;
            FFirmware = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnComPortClick(TObject *Sender)
{
        // 통신 설정창 열기 버튼
        FComPort = new TFormComPort(this);
        FComPort->ShowModal();
        if(FComPort)
        {
            delete FComPort;
            FComPort = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnLogClick(TObject *Sender)
{
        // 고급 설정창 열기 버튼
        //FormLog = new TFormLog(this);
        FormLog->Show();
        //if(FLog)
        //{
        //    delete FLog;
        //    FLog = NULL;
        //}
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnSignSizeClick(TObject *Sender)
{
        BtnSignSize->Enabled = false;
        if(cbProgramType->ItemIndex == 0)
            //ProgramType = e2BPP;
            FormMain->ProgramType = e3BPP;
        else if(cbProgramType->ItemIndex == 2)
            FormMain->ProgramType = e8BPP;
        //else if(cbProgramType->ItemIndex == 3)
        //    FormMain->ProgramType = e16BPP;
        else if(cbProgramType->ItemIndex == 3)
            FormMain->ProgramType = e24BPP;
        else //if(cbProgramType->ItemIndex == 3)
            FormMain->ProgramType = e3BPP;
        FormMain->ModuleHeight = UpDownHeight->Position;
        FormMain->ModuleWidth = UpDownWidth->Position;
        FormMain->Direct = cbDirection->ItemIndex;
        ButtonEnabled(false);
        FormMain->BtnSignSizeClick(this);
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::ButtonEnabled(bool bEnabled)
{
        // 버튼 활성화 함수
        BtnSignSize->Enabled = bEnabled;
        BtnComPort->Enabled = bEnabled;
        BtnFontSend->Enabled = bEnabled;
        BtnLEDSetting->Enabled = bEnabled;
        BtnDIBDDownload->Enabled = bEnabled;
        BtnLog->Enabled = bEnabled;
        BtnApply->Enabled = bEnabled;
        BtnOk->Enabled = bEnabled;
        this->Enabled = bEnabled;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::cbDirectCheckClick(TObject *Sender)
{
        //TntLabel10->Enabled = cbDirectCheck->Checked;
        cbDirection->Enabled = cbDirectCheck->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::TntLabel10Click(TObject *Sender)
{
        cbDirectCheck->Checked = !cbDirectCheck->Checked;
        //TntLabel10->Enabled = cbDirectCheck->Checked;
        cbDirection->Enabled = cbDirectCheck->Checked;
}
//---------------------------------------------------------------------------


void __fastcall TFormSetting::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::BtnETCClick(TObject *Sender)
{
        FormMain->FETC = new TFormETC(this);
        FormMain->FETC->ShowModal();
        if(FormMain->FETC)
        {
            delete FormMain->FETC;
            FormMain->FETC = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormSetting::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormSetting::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormSetting::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

