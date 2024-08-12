//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormDABITOption.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntStdCtrls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormDABITOption *FormDABITOption;
//---------------------------------------------------------------------------

__fastcall TFormDABITOption::TFormDABITOption(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption           = FormMain->LangFile.FormDABITOption;
        pnCaption->Caption = " " + this->Caption;
        BitBtnOk->Caption       = FormMain->LangFile.MessageButton10;
        DataSend->Caption       = FormMain->LangFile.BtnSend;
        rbWrite->Caption        = FormMain->LangFile.MessageButton17;
        rbRead->Caption         = FormMain->LangFile.MessageButton15;
        BtnComPort->Caption     = FormMain->LangFile.BtnPort;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        TTntLabel *tLabel;
        try{
            for(int i = 0; i < 10; i++)
            {
                tLabel = dynamic_cast<TTntLabel *>(FindComponent("laLabel" + IntToStr(i + 1)));
                if(tLabel)
                {
                    tLabel->Font->Color = FormMain->TextColor;
                    tLabel->Font->Name = FormMain->MainFont;
                    tLabel->Font->Size = FormMain->MainFontSize;
                }
            }
        }catch(...){
        }
        gbSubCom->Color = FormMain->MainRectangleColor;
        pnSubCom->Color = FormMain->MainColor;
        rbWrite->Font->Color = FormMain->TextColor;
        rbWrite->Font->Name = FormMain->MainFont;
        rbWrite->Font->Size = FormMain->MainFontSize;
        rbRead->Font->Color = FormMain->TextColor;
        rbRead->Font->Name = FormMain->MainFont;
        rbRead->Font->Size = FormMain->MainFontSize;
        cbDubug->Font->Name = FormMain->MainFont;
        cbDubug->Font->Size = FormMain->MainFontSize;
        cbBoardRate232->Font->Name = FormMain->MainFont;
        cbBoardRate232->Font->Size = FormMain->MainFontSize;
        cbBoardRateTTL->Font->Name = FormMain->MainFont;
        cbBoardRateTTL->Font->Size = FormMain->MainFontSize;
        cbBoardRate485->Font->Name = FormMain->MainFont;
        cbBoardRate485->Font->Size = FormMain->MainFontSize;
        cbBH1->Font->Name = FormMain->MainFont;
        cbBH1->Font->Size = FormMain->MainFontSize;
        cbJ4->Font->Name = FormMain->MainFont;
        cbJ4->Font->Size = FormMain->MainFontSize;
        DataSend->Font->Color = FormMain->TextBGColor;
        DataSend->Font->Name = FormMain->MainFont;
        DataSend->Font->Size = FormMain->MainFontSize;
        pnDataSend->Color = FormMain->ButtonColor;
        BtnComPort->Font->Color = clNavy;//TextBGColor;
        BtnComPort->Font->Name = FormMain->MainFont;
        BtnComPort->Font->Size = FormMain->MainFontSize;
        pnBtnComPort->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::rbWriteClick(TObject *Sender)
{
        //if(rbRead->Checked)
        //    cbDubug->Enabled = false;
        cbDubug->Enabled = rbWrite->Checked;
        cbBH1->Enabled = rbWrite->Checked;
        cbJ4->Enabled = rbWrite->Checked;
        cbBoardRate232->Enabled = rbWrite->Checked;
        cbBoardRateTTL->Enabled = rbWrite->Checked;
        cbBoardRate485->Enabled = rbWrite->Checked;
        laLabel3->Enabled = rbWrite->Checked;
        laLabel4->Enabled = rbWrite->Checked;
        laLabel5->Enabled = rbWrite->Checked;
        laLabel6->Enabled = rbWrite->Checked;
        laLabel7->Enabled = rbWrite->Checked;
        laLabel8->Enabled = rbWrite->Checked;
        laLabel9->Enabled = !rbWrite->Checked;
        laLabel10->Enabled = !rbWrite->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::DataSendClick(TObject *Sender)
{
        int Addr = 0;
        AnsiString asData = "";
        AnsiString asBluetoothData = "";
        Addr = FormMain->Controler1;
        DataSend->Enabled = false;
        FormMain->bStopSend = false;
        AnsiString asMsg = "";
        int DataSize = 0;
        if(rbRead->Checked)
        {
            asBluetoothData = "![" + FormMain->IntTo485Address(Addr) + "0B30!]";
            asMsg = "DABIT Functions Read";
        }
        else
        {
            asBluetoothData = "![" + FormMain->IntTo485Address(Addr) + "0B2 ";
            asBluetoothData = asBluetoothData + IntToStr(cbDubug->ItemIndex) + ",";
            asBluetoothData = asBluetoothData + IntToStr(cbBH1->ItemIndex) + ",";
            asBluetoothData = asBluetoothData + IntToStr(cbJ4->ItemIndex) + ",";
            asBluetoothData = asBluetoothData + IntToStr(cbBoardRate232->ItemIndex) + ",";
            asBluetoothData = asBluetoothData + IntToStr(cbBoardRateTTL->ItemIndex) + ",";
            asBluetoothData = asBluetoothData + IntToStr(cbBoardRate485->ItemIndex) + "!]";
            asMsg = "DABIT Functions Write";
        }
        int tempType = FormMain->PorotocolType;
        FormMain->PorotocolType = 1;
        DataSize = asBluetoothData.Length();
        BYTE *Data = new BYTE[DataSize];
        ZeroMemory(Data, DataSize);
        memcpy(Data, asBluetoothData.c_str(), DataSize);
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(!FormMain->ModuleASCIIData(FormMain->ComKind, Addr, Data, DataSize, true))
                FormMain->AddLog(asMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
            else
                FormMain->AddLog(asMsg + " [OK]" + FormMain->asLogMessage, clBlack);
            FormMain->ComClose(FormMain->ComKind);
        }
        if(Data)
        {
            delete [] Data;
            Data = NULL;
        }
        FormMain->PorotocolType = tempType;
        DataSend->Enabled = true;
        FormMain->bOptionDubug = cbDubug->ItemIndex;
        FormMain->bOptionBH1 = cbBH1->ItemIndex;
        FormMain->bOptionJ4 = cbJ4->ItemIndex;
        FormMain->bOptionBoardRate232 = cbBoardRate232->ItemIndex;
        FormMain->bOptionBoardRateTTL = cbBoardRateTTL->ItemIndex;
        FormMain->bOptionBoardRate485 = cbBoardRate485->ItemIndex;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::FormCreate(TObject *Sender)
{
        rbWriteClick(this);
        cbDubug->ItemIndex = FormMain->bOptionDubug;
        if(cbDubug->ItemIndex < 0)
            cbDubug->ItemIndex = 0;
        cbBH1->ItemIndex = FormMain->bOptionBH1;
        if(cbBH1->ItemIndex < 0)
            cbBH1->ItemIndex = 0;
        cbJ4->ItemIndex = FormMain->bOptionJ4;
        if(cbJ4->ItemIndex < 0)
            cbJ4->ItemIndex = 0;
        cbBoardRate232->ItemIndex = FormMain->bOptionBoardRate232;
        if(cbBoardRate232->ItemIndex < 0)
            cbBoardRate232->ItemIndex = 0;
        cbBoardRateTTL->ItemIndex = FormMain->bOptionBoardRateTTL;
        if(cbBoardRateTTL->ItemIndex < 0)
            cbBoardRateTTL->ItemIndex = 0;
        cbBoardRate485->ItemIndex = FormMain->bOptionBoardRate485;
        if(cbBoardRate485->ItemIndex < 0)
            cbBoardRate485->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::cbDubugClick(TObject *Sender)
{
        if(cbDubug->ItemIndex != 0)
        {
            int res = FormMain->Message("\r\n" + FormMain->LangFile.Message20 + "\r\n", FormMain->LangFile.MessageButton01, FormMain->LangFile.MessageButton02, "", FormMain->LangFile.Message20.Length() - 2, 3, 2, clBlack);
            if(res != mrYes)
                cbDubug->ItemIndex = 0;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::BtnComPortClick(TObject *Sender)
{
        FormMain->BtnComPort->Click();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::BitBtnOkClick(TObject *Sender)
{
        FormMain->bOptionDubug = cbDubug->ItemIndex;
        FormMain->bOptionBH1 = cbBH1->ItemIndex;
        FormMain->bOptionJ4 = cbJ4->ItemIndex;
        cbBoardRate232->ItemIndex = FormMain->bOptionBoardRate232;
        cbBoardRateTTL->ItemIndex = FormMain->bOptionBoardRateTTL;
        cbBoardRate485->ItemIndex = FormMain->bOptionBoardRate485;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITOption::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormDABITOption::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormDABITOption::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------
