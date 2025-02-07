//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormFontName.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma link "TntStdCtrls"
#pragma resource "*.dfm"
TFormFontName *FormFontName;
//---------------------------------------------------------------------------
__fastcall TFormFontName::TFormFontName(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption = FormMain->LangFile.FormFontNameCaption;
        pnCaption->Caption = " " + this->Caption;
        laFont1->Caption = FormMain->LangFile.FormFontLabel6 + "0";
        laFont2->Caption = FormMain->LangFile.FormFontLabel6 + "1";
        rgRead->Caption = FormMain->LangFile.MessageButton15;
        rgWrite->Caption = FormMain->LangFile.MessageButton17;
        BtnSend->Caption = FormMain->LangFile.BtnSend;
        BitBtnOk->Caption = FormMain->LangFile.MessageButton10;
        TTntLabel *Label;
        TEdit *EditName;
        AnsiString asFontName;
        for(int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                Label = dynamic_cast<TTntLabel*>(FindComponent("laFontLabel" + IntToStr(j + 1) + IntToStr(i + 1)));
                EditName = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Label)
                {
                    Label->Font->Color = FormMain->TextColor;
                    Label->Font->Name = FormMain->MainFont;
                    Label->Font->Size = FormMain->MainFontSize;
                    Label->Caption = FormMain->LangFile.FormFontLabel1 + IntToStr(i + 1);
                }
                if(EditName)
                {
                    EditName->Font->Color = FormMain->TextColor;
                    EditName->Font->Name = FormMain->MainFont;
                    EditName->Font->Size = FormMain->MainFontSize;
                    asFontName = ExtractFileName(FormMain->FontFileNameVersion[j][i]);
                    if(asFontName == "")
                        asFontName = ExtractFileName(FormMain->FontFileName[j][i]);
                    EditName->Text = asFontName;
                }
            }
        }
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;
        laFont1->Color = FormMain->MainColor;
        laFont1->Font->Color = FormMain->TextColor;
        laFont1->Font->Name = FormMain->MainFont;
        laFont1->Font->Size = FormMain->MainFontSize;
        gbFont1->Color = FormMain->MainRectangleColor;
        pnFont1->Color = FormMain->MainColor;
        laFont2->Color = FormMain->MainColor;
        laFont2->Font->Color = FormMain->TextColor;
        laFont2->Font->Name = FormMain->MainFont;
        laFont2->Font->Size = FormMain->MainFontSize;
        gbFont2->Color = FormMain->MainRectangleColor;
        pnFont2->Color = FormMain->MainColor;
        gbFontReadWrite->Color = FormMain->MainRectangleColor;
        pnFontReadWrite->Color = FormMain->MainColor;
        rgRead->Color = FormMain->MainColor;
        rgRead->Font->Color = FormMain->TextColor;
        rgRead->Font->Name = FormMain->MainFont;
        rgRead->Font->Size = FormMain->MainFontSize;
        rgWrite->Color = FormMain->MainColor;
        rgWrite->Font->Color = FormMain->TextColor;
        rgWrite->Font->Name = FormMain->MainFont;
        rgWrite->Font->Size = FormMain->MainFontSize;
        BtnSend->Font->Color = FormMain->TextBGColor;
        BtnSend->Font->Name = FormMain->MainFont;
        BtnSend->Font->Size = FormMain->MainFontSize;
        pnBtnSend->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::FormCreate(TObject *Sender)
{
        rgRead->Checked = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::BtnSendClick(TObject *Sender)
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
        BtnSend->Enabled = false;
        BYTE SendFileNameData[216];
        ZeroMemory(SendFileNameData, sizeof(SendFileNameData));
        AnsiString asFontName;
        int iSendSize = 0;
        TEdit *EditName;
        int iPos = 0;
        for(int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                EditName = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                asFontName = "";
                if(EditName)
                {
                    if(rgRead->Checked)
                        EditName->Text = "";
                    asFontName = ExtractFileName(EditName->Text);
                }
                if(asFontName == "")
                    asFontName = ExtractFileName(FormMain->FontFileName[j][i]);
                iSendSize = asFontName.Length();
                if(asFontName.Length() > 36)
                    asFontName = asFontName.SubString(1, iSendSize - 5) + "~" + asFontName.SubString(iSendSize - 4, 4);
                FormMain->FontFileNameVersion[j][i] = asFontName;
                memcpy(&SendFileNameData[iPos * 36], asFontName.c_str(), asFontName.Length());
                iPos++;
            }
        }
        bool bConnect = false;
        AnsiString TmpMsg = TmpMsg = "Font Name " + FormMain->LangFile.MessageButton15;
        if(rgWrite->Checked)
             TmpMsg = "Font Name " + FormMain->LangFile.MessageButton17;
        BYTE SendData[224];
        iSendSize = sizeof(SendData);
        int iTextLen = 0;
        ZeroMemory(SendData, sizeof(SendData));
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, true))
        {
            if(FormMain->PorotocolType == 1)
            {
                AnsiString asSendData = "";
                if(rgWrite->Checked)
                {
                    iPos = 0;
                    asSendData = "![" + FormMain->IntTo485Address(Addr) + "095 2";
                    for(int j = 0; j < 2; j++)
                    {
                        for(int i = 0; i < 3; i++)
                        {
                            asFontName = AnsiString((char*)&SendFileNameData[iPos * 36]).SubString(1, 36);
                            iTextLen = asFontName.Length();
                            if(iTextLen < 36)
                            {
                                for(int k = 0; k < 36 - iTextLen; k++)
                                    asFontName += " ";
                            }
                            asSendData += asFontName;
                            iPos++;
                        }
                    }
                    asSendData += "!]";
                }
                else
                    asSendData = "![" + FormMain->IntTo485Address(Addr) + "0960!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                bConnect = FormMain->ModuleASCIIData(FormMain->ComKind, Addr, SendData, iSendSize, true);
            }
            else
            {
                SendData[0] = 0x00;
                SendData[1] = 0x32;
                if(rgWrite->Checked)
                {
                    memcpy(&SendData[2], SendFileNameData, sizeof(SendFileNameData));
                    iSendSize = 2 + sizeof(SendFileNameData);
                }
                else
                {
                    SendData[0] = 0x01;
                    iSendSize = 2;
                }
                //if(rgWrite->Checked)
                //    bConnect = FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, 0x45, true);
                //else
                    bConnect = FormMain->ModuleData(FormMain->ComKind, Addr, SendData, iSendSize, 0, (OPCode)FRW, true);
            }
            if(!bConnect)
                FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
            else
            {
                FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                if(rgRead->Checked)
                {
                    TEdit *EditName;
                    for(int j = 0; j < 2; j++)
                    {
                        for(int i = 0; i < 3; i++)
                        {
                            EditName = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                            if(EditName)
                                EditName->Text = ExtractFileName(FormMain->FontFileNameVersion[j][i]);
                        }
                    }
                }
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        else
            FormMain->AddLog(FormMain->LangFile.Message15, clRed);
        BtnSend->Enabled = true;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::BitBtnOkClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::rgReadWriteClick(TObject *Sender)
{
        TEdit *EditName;
        TTntLabel *Label;
        for(int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                EditName = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(EditName)
                {
                    if(rgRead->Checked)
                        EditName->Enabled = false;
                    else
                        EditName->Enabled = true;
                }
                Label = dynamic_cast<TTntLabel*>(FindComponent("laFontLabel" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Label)
                {
                    if(rgRead->Checked)
                        Label->Enabled = false;
                    else
                        Label->Enabled = true;
                }
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormFontName::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormFontName::pnCaptionMouseUp(TObject *Sender,
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

