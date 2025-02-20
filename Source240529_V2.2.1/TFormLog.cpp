//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormLog.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntExtCtrls"
#pragma link "TntButtons"
#pragma resource "*.dfm"
TFormLog *FormLog;
//---------------------------------------------------------------------------
__fastcall TFormLog::TFormLog(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption = FormMain->LangFile.FormLog;
        pnCaption->Caption = " " + this->Caption;
        pnLog->Caption = " " + FormMain->LangFile.BtnLog;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnLog->Color = FormMain->MainColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        pnLogCaption->Color = FormMain->CaptionPanelColor;
        pnBtnLogButton->Color = FormMain->CaptionPanelColor;
        pnLogCaption->Font->Color = FormMain->TextColor;
        pnLogCaption->Font->Name = FormMain->MainFont;
        pnLogCaption->Font->Size = FormMain->MainFontSize;
        RichEditLog->Font->Name = FormMain->MainFont;
        RichEditLog->Font->Size = FormMain->MainFontSize;
}
//---------------------------------------------------------------------------

void __fastcall TFormLog::FormCreate(TObject *Sender)
{
        FormMain->DeleteOldLogFile(FormMain->BasePath + FormMain->DestLogPath + "\\");
        AnsiString FileName = FormMain->BasePath + FormMain->DestLogPath + "\\" + FormatDateTime("YYYYMMDD", Now()) + "_" + IntToStr(FormMain->LogFileCnt) + ".log";
        TFileStream * FS = NULL;
        BYTE *Buff = NULL;
        try{
            if(FileExists(FileName))
                FS = new TFileStream(FileName, fmOpenReadWrite | fmShareCompat);
            else
            {
                FormMain->DeleteOldLogFile(FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestLogPath));
                FileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestLogPath) + FormatDateTime("YYYYMMDD", Now()) + "_" + IntToStr(FormMain->LogFileCnt) + ".log";
                FS = new TFileStream(FileName, fmCreate | fmShareCompat);
            }
            Buff = new BYTE[FS->Size];
            ZeroMemory(Buff, FS->Size);
            FS->Read(Buff, FS->Size);
            AnsiString asBuff = AnsiString((char *)Buff).SubString(1, FS->Size);
            RichEditLog->Lines->Add(asBuff);
            //FormMain->RichEditLog->Lines->Add(asBuff);
            SendMessage(RichEditLog->Handle, WM_VSCROLL, SB_BOTTOM, 0);
        }catch (...){
        }
        if(Buff)
        {
            delete [] Buff;
            Buff = NULL;
        }
        if(FS)
        {
            delete FS;
            FS = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormLog::BtnLogClearClick(TObject *Sender)
{
        //AnsiString LogFileName = "";
        //LogFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestLogPath) + FormatDateTime("YYYYMMDD", Now()) + "_" + IntToStr(FormMain->LogFileCnt) + ".log";
        //if(FileExists(LogFileName))
        //    DeleteFile(LogFileName);
        RichEditLog->Clear();
        FormMain->RichEditLog->Clear();
}
//---------------------------------------------------------------------------

void __fastcall TFormLog::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormLog::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormLog::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormLog::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormLog::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormLog::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

