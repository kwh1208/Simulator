//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormMessage.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntStdCtrls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormMessage *FormMessage;
//---------------------------------------------------------------------------
//메세지 초기값 설정
__fastcall TFormMessage::TFormMessage(TComponent* Owner)
        : TForm(Owner)
{
        //this->Caption = "메세지 창";
        pnCaption->Caption = " " + this->Caption;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        laMessage->Font->Color = FormMain->TextColor;
        laMessage->Font->Name = FormMain->MainFont;
        laMessage->Font->Size = FormMain->MainFontSize;
        btnYes->Font->Color = FormMain->TextBGColor;
        btnYes->Font->Name = FormMain->MainFont;
        btnYes->Font->Size = FormMain->MainFontSize;
        pnbtnYes->Color = FormMain->ButtonColor;
        btnNo->Font->Color = FormMain->TextBGColor;
        btnNo->Font->Name = FormMain->MainFont;
        btnNo->Font->Size = FormMain->MainFontSize;
        pnbtnNo->Color = FormMain->ButtonColor;
        btnCancel->Font->Color = FormMain->TextBGColor;
        btnCancel->Font->Name = FormMain->MainFont;
        btnCancel->Font->Size = FormMain->MainFontSize;
        pnbtnCancel->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormMessage::btnYesClick(TObject *Sender)
{
        this->Tag = mrYes;
        Close();        
}
//---------------------------------------------------------------------------

void __fastcall TFormMessage::btnNoClick(TObject *Sender)
{
        this->Tag = mrNo;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormMessage::btnCancelClick(TObject *Sender)
{
        this->Tag = mrCancel;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormMessage::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormMessage::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormMessage::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormMessage::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormMessage::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormMessage::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

