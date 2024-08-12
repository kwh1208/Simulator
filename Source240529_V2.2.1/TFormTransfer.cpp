//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormTransfer.h"  
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "SUIProgressBar"
#pragma link "TntStdCtrls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormTransfer *FormTransfer;
//---------------------------------------------------------------------------
__fastcall TFormTransfer::TFormTransfer(TComponent* Owner)
        : TForm(Owner)
{
        Caption = FormMain->LangFile.FormTransferCaption1;//"송신 시작";
        pnCaption->Caption = " " + this->Caption;
        //LabelName->Caption = "파일을 전송을 시작합니다.";
        Label6->Caption = FormMain->LangFile.FormTransferLabel1;
        Label1->Caption = FormMain->LangFile.FormTransferLabel2;
        BCancel->Caption = FormMain->LangFile.MessageButton03;
        //Update();
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        LabelSign->Font->Color = FormMain->TextColor;
        LabelSign->Font->Name = FormMain->MainFont;
        LabelSign->Font->Size = FormMain->MainFontSize;
        LabelCount->Font->Color = FormMain->TextColor;
        LabelCount->Font->Name = FormMain->MainFont;
        LabelCount->Font->Size = FormMain->MainFontSize;
        LabelName->Font->Color = FormMain->TextColor;
        LabelName->Font->Name = FormMain->MainFont;
        LabelName->Font->Size = FormMain->MainFontSize;
        Label6->Font->Color = FormMain->TextColor;
        Label6->Font->Name = FormMain->MainFont;
        Label6->Font->Size = FormMain->MainFontSize;
        Label1->Font->Color = FormMain->TextColor;
        Label1->Font->Name = FormMain->MainFont;
        Label1->Font->Size = FormMain->MainFontSize;
        LabelTotPercent->Font->Color = FormMain->TextColor;
        LabelTotPercent->Font->Name = FormMain->MainFont;
        LabelTotPercent->Font->Size = FormMain->MainFontSize;
        LabelTotalPacket->Font->Color = FormMain->TextColor;
        LabelTotalPacket->Font->Name = FormMain->MainFont;
        LabelTotalPacket->Font->Size = FormMain->MainFontSize;
        BCancel->Font->Color = FormMain->TextBGColor;
        BCancel->Font->Name = FormMain->MainFont;
        BCancel->Font->Size = FormMain->MainFontSize;
        pnBCancel->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormTransfer::FormClose(TObject *Sender,
      TCloseAction &Action)
{
        Caption = FormMain->LangFile.FormTransferCaption2;//"송신 끝";
        pnCaption->Caption = " " + this->Caption;
        //LabelName->Caption = "파일을 전송을 끝냈습니다.";
        //Update();
}
//---------------------------------------------------------------------------

void __fastcall TFormTransfer::FormShow(TObject *Sender)
{
        Caption = FormMain->LangFile.FormTransferCaption3;//"송신 중...";
        pnCaption->Caption = " " + this->Caption;
        //Update();
}
//---------------------------------------------------------------------------

void __fastcall TFormTransfer::BCancelClick(TObject *Sender)
{
        FormMain->bStopSend = true;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormTransfer::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormTransfer::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormTransfer::btnExitClick(TObject *Sender)
{
        FormMain->bStopSend = true;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormTransfer::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormTransfer::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormTransfer::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

