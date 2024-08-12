//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormFont.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntStdCtrls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormFont *FormFont;
int FontKindAddr[2][8] = {{0, 0, 0xac00, 0x3040, 0x4e00, 0x8861, 0xe000, 0}, {0, 0x7f, 0xd7a3, 0x30ff, 0x9fff, 0xd3bd, 0xe07f, 0xd7a3}};
//---------------------------------------------------------------------------

__fastcall TFormFont::TFormFont(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption = FormMain->LangFile.FormFontCaption;
        pnCaption->Caption = " " + this->Caption;
        laFontLabel1->Caption = FormMain->LangFile.FormFontLabel1;
        laFontLabel6->Caption = FormMain->LangFile.FormFontLabel1;
        laFontLabel2->Caption = FormMain->LangFile.FormFontLabel2;
        laFontLabel3->Caption = FormMain->LangFile.FormFontLabel3;
        laFontLabel7->Caption = FormMain->LangFile.FormFontLabel1;
        laFontLabel8->Caption = FormMain->LangFile.FormFontLabel4;
        laFontLabel9->Caption = FormMain->LangFile.FormFontLabel1;
        laFontLabel10->Caption = FormMain->LangFile.FormFontLabel5;
        TTntLabel *Label;
        TTntPanel *pnBtnButton;
        TTntComboBox *ComboBox;
        TTntComboBox *ComboBox2;
        TTntCheckBox *CheckBox;
        TTntSpeedButton *SpeedButton;
        for(int i = 0; i < 10; i++)
        {
            Label = dynamic_cast<TTntLabel*>(FindComponent("laFontLabel" + IntToStr(i + 1)));
            if(Label)
            {
                Label->Font->Color = FormMain->TextColor;
                Label->Font->Name = FormMain->MainFont;
                Label->Font->Size = FormMain->MainFontSize;
            }
        }
        for(int j = 0; j < 4; j++)
        {
            CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
            if(CheckBox)
            {
                CheckBox->Color = FormMain->MainColor;
                CheckBox->Font->Color = FormMain->TextColor;
                CheckBox->Font->Name = FormMain->MainFont;
                CheckBox->Font->Size = FormMain->MainFontSize;
            }
            ComboBox2 = dynamic_cast<TTntComboBox*>(FindComponent("cbFontSize" + IntToStr(j + 1)));
            if(ComboBox2)
            {
                ComboBox2->Font->Name = FormMain->MainFont;
                ComboBox2->Font->Size = FormMain->MainFontSize;
            }
            for(int i = 0; i < 3; i++)
            {
                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(ComboBox)
                {
                    ComboBox->Clear();
                    ComboBox->Items->Add(FormMain->LangFile.FormProtocolEffect1);
                    if(i == 0)
                        ComboBox->Items->Add(FormMain->LangFile.FormFontItem[0]);
                    else if(i == 2)
                    {
                        ComboBox->Items->Add(FormMain->LangFile.FormFontItem[5]);
                        //ComboBox->Items->Add(FormMain->LangFile.FormFontItem[6]);
                    }
                    else
                    {
                        for(int k = 1; k < 5; k++)
                        {
                            ComboBox->Items->Add(FormMain->LangFile.FormFontItem[k]);
                        }
                        ComboBox->Items->Add(FormMain->LangFile.FormFontItem[6]);
                    }
                    ComboBox->Font->Name = FormMain->MainFont;
                    ComboBox->Font->Size = FormMain->MainFontSize;
                    ComboBox->ItemIndex = -1;
                    ComboBox->ItemIndex = 0;
                }
                SpeedButton = dynamic_cast<TTntSpeedButton*>(FindComponent("sbPathFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(SpeedButton)
                {
                    SpeedButton->Font->Color = FormMain->TextBGColor;
                    SpeedButton->Font->Name = FormMain->MainFont;
                    SpeedButton->Font->Size = FormMain->MainFontSize;
                }
                Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Label)
                {
                    Label->Font->Color = FormMain->TextColor;
                    Label->Font->Name = FormMain->MainFont;
                    Label->Font->Size = FormMain->MainFontSize;
                }
                pnBtnButton = dynamic_cast<TTntPanel*>(FindComponent("pnsbPathFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(pnBtnButton)
                    pnBtnButton->Color = FormMain->ButtonColor;
            }
        }
        BtnRead->Caption = FormMain->LangFile.FormFontLabel7;
        BtnSend->Caption = FormMain->LangFile.BtnSend;
        BitBtnOk->Caption = FormMain->LangFile.MessageButton10;
        //rgFontSetup->Caption = FormMain->LangFile.FormFontLabel6;
        cbFont1->Caption = FormMain->LangFile.FormFontLabel6 + "0";
        cbFont2->Caption = FormMain->LangFile.FormFontLabel6 + "1";
        cbFont3->Caption = FormMain->LangFile.FormFontLabel6 + "2";
        cbFont4->Caption = FormMain->LangFile.FormFontLabel6 + "3";
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        gbFont1->Color = FormMain->MainRectangleColor;
        pnFont1->Color = FormMain->MainColor;
        gbFont2->Color = FormMain->MainRectangleColor;
        pnFont2->Color = FormMain->MainColor;
        gbFont3->Color = FormMain->MainRectangleColor;
        pnFont3->Color = FormMain->MainColor;
        gbFont4->Color = FormMain->MainRectangleColor;
        pnFont4->Color = FormMain->MainColor;
        laTotFontSize->Font->Color = FormMain->TextColor;
        laTotFontSize->Font->Name = FormMain->MainFont;
        laTotFontSize->Font->Size = FormMain->MainFontSize;
        BtnRead->Font->Color = FormMain->TextBGColor;
        BtnRead->Font->Name = FormMain->MainFont;
        BtnRead->Font->Size = FormMain->MainFontSize;
        pnBtnRead->Color = FormMain->ButtonColor;
        BtnSend->Font->Color = FormMain->TextBGColor;
        BtnSend->Font->Name = FormMain->MainFont;
        BtnSend->Font->Size = FormMain->MainFontSize;
        pnBtnSend->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
        /*if(!gbFont4->Visible)
        {
            laTotFontSize->Top = gbFont2->Top + gbFont2->Height + 13;
            pnBitBtnOk->Top = gbFont2->Top + gbFont2->Height + 17;
            pnBtnSend->Top = pnBitBtnOk->Top;
            pnBtnRead->Top = pnBitBtnOk->Top;
            pnMain->Height = pnBitBtnOk->Top + pnBitBtnOk->Height + 17;
            pnMainDivide->Height = pnMain->Height + 8;
            this->Height = pnMainDivide->Height;
            this->Width = pnMainDivide->Width;
        }*/
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::FormCreate(TObject *Sender)
{
        AnsiString TempPath = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestFontPath);
        AnsiString schName = "";
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        TTntComboBox *ComboBox2;
        TTntCheckBox *CheckBox;
        TTntSpeedButton *SpeedButton;
        int iTotFontSize = 0;
        bool bChecked = false;
        for(int j = 0; j < 4; j++)
        {
            CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
            if(CheckBox)
            {
                if(j == 0)
                    bChecked = true;
                else
                    bChecked = CheckBox->Checked;
            }
            for(int i = 0; i < 3; i++)
            {
                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(ComboBox)
                {
                    ComboBox->ItemIndex = ComboBox->Items->IndexOf(FindCodeIndexString(FormMain->FontIndex[j][i]));
                    if(ComboBox->ItemIndex < 0)
                        ComboBox->ItemIndex = 0;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    Edit->Font->Name = FormMain->MainFont;
                    Edit->Font->Size = FormMain->MainFontSize;
                    schName = TempPath + ExtractFileName(FormMain->FontFileName[j][i]);
                    if(FileExists(schName))
                    {
                        Edit->Text = schName;
                        Edit->SelStart = Edit->Text.Length();
                        Edit->SelLength = 0;
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {     
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                ComboBox2 = dynamic_cast<TTntComboBox*>(FindComponent("cbFontSize" + IntToStr(j + 1)));
                                if(ComboBox && ComboBox2)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(ComboBox2->ItemIndex == 2)
                                            {
                                                if(bChecked)
                                                    iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 128;
                                                Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 128) + " Byte";
                                            }
                                            else
                                            {
                                                if(bChecked)
                                                    iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                                Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                            }
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
        cbFont2->Checked = FormMain->FontCountCheck[1];
        cbFont2Click(this);
        cbFont3->Checked = FormMain->FontCountCheck[2];
        cbFont3Click(this);
        cbFont4->Checked = FormMain->FontCountCheck[3];
        cbFont4Click(this);
        if(!gbFont4->Visible)
        {
            cbFont3->Checked = false;
            cbFont4->Checked = false;
        }
        cbFontSize1->ItemIndex = 1;//FormMain->FontSizeIndex[0];
        cbFontSize2->ItemIndex = FormMain->FontSizeIndex[1];
        cbFontSize3->ItemIndex = FormMain->FontSizeIndex[2];
        cbFontSize4->ItemIndex = FormMain->FontSizeIndex[3];
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::sbPathFontClick(TObject *Sender)
{
        TTntSpeedButton *SpeedButton;
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        TTntComboBox *ComboBox2;
        bool bExistButton = false;
        int iPos1 = 0;
        int iPos2 = 0;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                SpeedButton = dynamic_cast<TTntSpeedButton*>(FindComponent("sbPathFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(SpeedButton)
                {
                    if(Sender == SpeedButton)
                    {
                        bExistButton = true;
                        iPos1 = j;
                        iPos2 = i;
                        break;
                    }
                }
            }
            if(bExistButton)
                break;
        }
        int iCodeIndex = 0;
        if(bExistButton)
        {
            FormMain->OpenDialog->Options.Clear();
            FormMain->OpenDialog->Files->Clear();
            FormMain->OpenDialog->Options << ofFileMustExist << ofNoChangeDir;
            FormMain->OpenDialog->FileName = "";
            FormMain->OpenDialog->FilterIndex = 1;
            FormMain->OpenDialog->Filter = "Font File (*.fnt)|*.fnt|All File (*.*)|*.*";
            FormMain->OpenDialog->DefaultExt = "fnt";
            FormMain->OpenDialog->Title = "Open Font File";
            FormMain->OpenDialog->InitialDir = FormMain->BasePath + FormMain->DestFontPath;
            if(!DirectoryExists(FormMain->OpenDialog->InitialDir))
                CreateDir(FormMain->OpenDialog->InitialDir);
            if(ExtractFileName(FormMain->FontFileName[iPos1][iPos2]) != "" && ExtractFileName(FormMain->FontFileName[iPos1][iPos2]) != " ")
            {
                if(FileExists(FormMain->OpenDialog->InitialDir + "\\" + ExtractFileName(FormMain->FontFileName[iPos1][iPos2])))
                    FormMain->OpenDialog->FileName = FormMain->OpenDialog->InitialDir + "\\" + ExtractFileName(FormMain->FontFileName[iPos1][iPos2]);
            }
            if(FormMain->OpenDialog->Execute())
            {
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(iPos1 + 1) + IntToStr(iPos2 + 1)));
                Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(iPos1 + 1) + IntToStr(iPos2 + 1)));
                if(Edit && Label)
                {
                    AnsiString FileName = FormMain->OpenDialog->FileName;
                    TFileStream *File = NULL;
                    if(FileExists(FileName))
                    {
                        try{
                            File = new TFileStream(FileName, fmOpenRead | fmShareCompat);
                            BYTE tempHeader[16];
                            ZeroMemory(tempHeader, sizeof(tempHeader));
                            ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(iPos1 + 1) + IntToStr(iPos2 + 1)));
                            ComboBox2 = dynamic_cast<TTntComboBox*>(FindComponent("cbFontSize" + IntToStr(iPos1 + 1)));
                            File->Read(tempHeader, 16);
                            if(ComboBox)
                            {
                                if(ComboBox->ItemIndex != 0)
                                {
                                    iCodeIndex = FindCodeIndex(ComboBox->Text);
                                    bool bError = false;
                                    if(iCodeIndex == 1)
                                    {
                                        if(ComboBox2->ItemIndex == 1 && (tempHeader[12] != 0x08 || tempHeader[13] != 0x10))
                                            bError = true;
                                        else if(ComboBox2->ItemIndex == 2 && (tempHeader[12] != 0x10 || tempHeader[13] != 0x20))
                                            bError = true;
                                        else if(ComboBox2->ItemIndex == 3 && (tempHeader[12] != 0x18 || tempHeader[13] != 0x30))
                                            bError = true;
                                        else if(ComboBox2->ItemIndex == 4 && (tempHeader[12] != 0x20 || tempHeader[13] != 0x40))
                                            bError = true;
                                    }
                                    else
                                    {
                                        if(ComboBox2->ItemIndex == 2 && (tempHeader[12] != 0x20 || tempHeader[13] != 0x20))
                                            bError = true;
                                        else
                                        {
                                            //if(tempHeader[12] != 0x10 || tempHeader[13] != 0x10)
                                            //    bError = true;
                                        }
                                    }
                                    if(bError)
                                    {
                                        FormMain->Message("\r\n" + FormMain->LangFile.Message19 + "\r\n", FormMain->LangFile.MessageButton04, "", "", FormMain->LangFile.Message19.Length() - 2, 3, 1, clRed);
                                        if(File)
                                        {
                                            delete File;
                                            File = NULL;
                                        }
                                        return;
                                    }
                                    if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                    {
                                        if(ComboBox2->ItemIndex == 2)
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 128) + " Byte";
                                        else
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                    }
                                    else
                                        Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                                else
                                    Label->Caption = "0 Byte";
                            }
                            else
                                Label->Caption = IntToStr(File->Size - 16) + " Byte";
                            Edit->Text = FormMain->OpenDialog->FileName;
                            Edit->SelStart = Edit->Text.Length();
                            Edit->SelLength = 0;
                            FormMain->FontFileName[iPos1][iPos2] = FormMain->OpenDialog->FileName;
                        }catch(...){
                        }
                        if(File)
                        {
                            delete File;
                            File = NULL;
                        }
                    }
                }
            }
            TEdit *Edit;
            int iTotFontSize = 0;
            bool bChecked = false;
            TTntCheckBox *CheckBox;
            for(int j = 0; j < 4; j++)
            {
                for(int i = 0; i < 3; i++)
                {
                    if(j == 0)
                        bChecked = true;
                    else
                    {
                        CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                        if(CheckBox)
                            bChecked = CheckBox->Checked;
                    }
                    if(bChecked)
                    {
                        Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Edit)
                        {
                            AnsiString schName = Edit->Text;
                            if(FileExists(schName))
                            {
                                TFileStream *File = NULL;
                                try{
                                    File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                    ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                    ComboBox2 = dynamic_cast<TTntComboBox*>(FindComponent("cbFontSize" + IntToStr(j + 1)));
                                    if(ComboBox)
                                    {
                                        if(ComboBox->ItemIndex != 0)
                                        {
                                            iCodeIndex = FindCodeIndex(ComboBox->Text);
                                            if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                            {
                                                if(ComboBox2->ItemIndex == 2)
                                                    iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 128;
                                                else
                                                    iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            }
                                            else
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                        }
                                    }
                                    else
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                }catch(...){
                                }
                                if(File)
                                {
                                    delete File;
                                    File = NULL;
                                }
                            }
                        }
                    }
                }
            }
            laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::BtnSendClick(TObject *Sender)
{
        int Addr = 0;
        AnsiString asData = "";
        int GroupCnt = 0;
        int SignIndex = 0;
        Addr = FormMain->Controler1;
        this->Enabled = false;
        FormMain->bStopSend = false;
        BtnSend->Enabled = false;
        AnsiString FontMessage = "DABIT Font - ";
        int iWFontCnt = 0;
        TTntCheckBox *CheckBox;
        TTntComboBox *ComboBox;
        TEdit *Edit;
        bool bChecked = false;
        int iTotFontSize = 0;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                if(bChecked)
                {
                    Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                    if(Edit)
                    {
                        if(Edit->Text != "" && Edit->Text != " ")
                        {
                            ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                            if(ComboBox)
                            {
                                if(ComboBox->ItemIndex != 0)
                                {
                                    FontMessage += ExtractFileName(Edit->Text);
                                    FontMessage += ", ";
                                    AnsiString schName = Edit->Text;
                                    if(FileExists(schName))
                                    {
                                        TFileStream *File = NULL;
                                        try{
                                            File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                            iTotFontSize = iTotFontSize + File->Size - 16;
                                        }catch(...){
                                        }
                                        if(File)
                                        {
                                            delete File;
                                            File = NULL;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /*if(iTotFontSize > 3145728)
        {
            FormMain->Message("\r\n" + FormMain->LangFile.Message18 + "\r\n", FormMain->LangFile.MessageButton04, "", "", FormMain->LangFile.Message18.Length() - 2, 3, 1, clRed);
            this->Enabled = true;
            BtnSend->Enabled = true;
            this->BringToFront();
            return;
        }*/
        AnsiString TmpMsg = "";
        BYTE SendData[10];
        ZeroMemory(SendData, sizeof(SendData));
        SendData[0] = 0xF1;
        SendData[1] = FormMain->ProgramType;
        SendData[2] = StrToIntDef("0x" + FormMain->VerMj[0], 0);
        SendData[3] = StrToIntDef("0x" + FormMain->VerMj[1] + FormMain->VerMj[2], 0);
        SendData[4] = (BYTE)char('N');
        SendData[5] = 0x00;
        SendData[6] = GetFontValue(cbFontSize1->ItemIndex);
        if(cbFont2->Checked)
            SendData[7] = GetFontValue(cbFontSize2->ItemIndex);
        if(cbFont3->Checked)
            SendData[8] = GetFontValue(cbFontSize3->ItemIndex);
        if(cbFont4->Checked)
            SendData[9] = GetFontValue(cbFontSize4->ItemIndex);
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(!FormMain->ModuleData(FormMain->ComKind, FormMain->Controler1, SendData, sizeof(SendData), 0, FDPI, true))
                FormMain->AddLog("DABIT Version Request [Receive : FAIL]" + FormMain->asLogMessage, clRed);
            else
            {
                FormMain->AddLog("DABIT Version Request [Receive : OK]" + FormMain->asLogMessage, clBlack);
                FormMain->NoneDelayCount(500);
            }
            TmpMsg = "Display STOP";
            SendData[0] = DispOff;
            if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, 1, 0, DC, true))
            {
                FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                FormMain->AddLog(FontMessage, clBlack);
                if(iWFontCnt < 3)
                    SendFontData(FormMain->ComKind, Addr);
            }
            else
                FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
            TmpMsg = "Display Start";
            SendData[0] = DispOn;
            if(FormMain->ModuleData(FormMain->ComKind, Addr, SendData, 1, 0, DC, true))
                FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
            else
                FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
            FormMain->ComClose(FormMain->ComKind);
        }
        FormMain->FontCountCheck[1] = cbFont2->Checked;
        FormMain->FontCountCheck[2] = cbFont3->Checked;
        FormMain->FontCountCheck[3] = cbFont4->Checked;
        FormMain->FontSizeIndex[0] = cbFontSize1->ItemIndex;
        FormMain->FontSizeIndex[1] = cbFontSize2->ItemIndex;
        FormMain->FontSizeIndex[2] = cbFontSize3->ItemIndex;
        FormMain->FontSizeIndex[3] = cbFontSize4->ItemIndex;
        this->Enabled = true;
        BtnSend->Enabled = true;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::BtnReadClick(TObject *Sender)
{
        // 통신 설정창 열기 버튼
        FFontName = new TFormFontName(this);
        FFontName->Top = this->Top;
        FFontName->Left = this->Left + (this->Width - FFontName->Width) / 2;
        FFontName->ShowModal();
        if(FFontName)
        {
            delete FFontName;
            FFontName = NULL;
        }
}
//---------------------------------------------------------------------------

int __fastcall TFormFont::GetFontValue(int iIndex)
{
        int iValue = 3;
        if(iIndex == 0)
            iValue = 0;
        else if(iIndex == 2)
            iValue = 7;
        else if(iIndex == 3)
            iValue = 11;
        else if(iIndex == 4)
            iValue = 15;
        else if(iIndex == 5)
            iValue = 17;
        return iValue;
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::BitBtnOkClick(TObject *Sender)
{
        TEdit *Edit;
        TTntCheckBox *CheckBox;
        TTntComboBox *ComboBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(ComboBox)
                    FormMain->FontIndex[j][i] = FindCodeIndex(ComboBox->Text);
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                    FormMain->FontFileName[j][i] = Edit->Text;
            }
        }
        FormMain->FontCountCheck[1] = cbFont2->Checked;
        FormMain->FontCountCheck[2] = cbFont3->Checked;
        FormMain->FontCountCheck[3] = cbFont4->Checked;
        FormMain->FontSizeIndex[0] = cbFontSize1->ItemIndex;
        FormMain->FontSizeIndex[1] = cbFontSize2->ItemIndex;
        FormMain->FontSizeIndex[2] = cbFontSize3->ItemIndex;
        FormMain->FontSizeIndex[3] = cbFontSize4->ItemIndex;
        Close();
}
//---------------------------------------------------------------------------

bool __fastcall TFormFont::SendFontData(BYTE ComKind, int Addr)
{
        bool Connect = false;
        int TotBlockCnt = 0;
        int FontBlockCnt[4][3];
        int TotPacketCnt = 0;
        int PacketCnt = 0;
        AnsiString FileName = "";
        int DataSize = 1024;
        TFileStream *File = NULL;
        BYTE *NData = NULL;
        //int FontKindBlockCnt[8] = {0, 128 * 16, 11171 * 32, 191 * 32, 20991 * 32, 20290 * 32, 127 * 32, 55203 * 32};
        //int FontKindBlockCnt[8] = {0, 2048, 357472, 6112, 671712, 649280, 4064, 1766496};
        int FontKindBlockCnt[8];
        int iCodeIndex = 0;
        int iFontCount = 1;
        if(cbFont2->Checked)
            iFontCount++;
        if(cbFont3->Checked)
            iFontCount++;
        if(cbFont4->Checked)
            iFontCount++;
        TEdit *Edit;
        TTntComboBox *ComboBox;
        TTntComboBox *ComboBox2;
        try{
            int TopSize = 0x7f;
            for(int j = 0; j < iFontCount; j++)
            {
                for(int i = 0; i < 3; i++)
                {
                    ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) +IntToStr(i + 1)));
                    iCodeIndex = FindCodeIndex(ComboBox->Text);
                    if(iCodeIndex == 6)
                    {
                        Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                        FileName = Edit->Text;
                        if(FileExists(FileName))
                        {
                            File = new TFileStream(FileName, fmOpenRead | fmShareCompat);
                            int iFileSie = File->Size - 16;
                            iFileSie = iFileSie / 32;
                            if(iFileSie > TopSize)
                            {
                                TopSize = iFileSie;
                                FontKindAddr[1][6] = 0xe000 + iFileSie;
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                }
            }
        }catch(...){
        }
        for(int i = 1; i < 8; i++)
        {
            if(i == 1)
                FontKindBlockCnt[i] = ((FontKindAddr[1][i] - FontKindAddr[0][i]) + 1) * 16;
            else
                FontKindBlockCnt[i] = ((FontKindAddr[1][i] - FontKindAddr[0][i]) + 1) * 32;
            if(FontKindBlockCnt[i] % DataSize == 0)
               FontKindBlockCnt[i] = FontKindBlockCnt[i] / DataSize;
            else
               FontKindBlockCnt[i] = FontKindBlockCnt[i] / DataSize + 1;
        }
        int FontStartAddr[2][3];
        union{
            int iSize;
            char cSize[4];
        }uSize;
        tsFontData FontData;
        ZeroMemory(&FontData, sizeof(FontData));
        FontData.FontCnt = iFontCount;
        bool FileSend[4][3];
        bool bAllSend[3];
        bAllSend[0] = false;
        bAllSend[1] = false;
        bAllSend[2] = false;
        AnsiString TmpMsg = "";
        try
        {
            for(int j = 0; j < iFontCount; j++)
            {
                for(int i = 0; i < 3; i++)
                {
                    Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                    //if(j == 1)
                    //    ComboBox2 = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) +IntToStr(i + 1)));
                    //else
                        ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) +IntToStr(i + 1)));
                    ComboBox2 = dynamic_cast<TTntComboBox*>(FindComponent("cbFontSize" + IntToStr(j + 1)));
                    FontBlockCnt[j][i] = 0;
                    FontStartAddr[j][i] = 0;
                    FileSend[j][i] = false;
                    if(Edit && (ComboBox || ComboBox2))
                    {
                        //if(j == 1)
                        //{
                        //    if(ComboBox2->ItemIndex == 0)
                        //        continue;
                        //    iCodeIndex = FindCodeIndex(ComboBox2->Text);
                        //}
                        //else
                        //{
                            if(ComboBox->ItemIndex == 0)
                                continue;
                            iCodeIndex = FindCodeIndex(ComboBox->Text);
                        //}
                        if(iCodeIndex == 0)
                            continue;
                        FileName = Edit->Text;
                        if(ExtractFileName(FileName) != "" && ExtractFileName(FileName) != " ")
                        {
                            if(!FileExists(FileName))
                            {
                                FileName = FormMain->BasePath + FormMain->DestFontPath + "\\" + ExtractFileName(FileName);
                                if(!FileExists(FileName))
                                {
                                    this->Enabled = true;
                                    BtnSend->Enabled = true;
                                    TmpMsg = ExtractFileName(FileName) + " " + FormMain->LangFile.Message15;
                                    FormMain->Message("\r\n" + TmpMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", TmpMsg.Length() - 2, 3, 1, clBlack);
                                    return false;
                                }
                                Edit->Text = FileName;
                            }
                            bAllSend[i] = false;
                            FileSend[j][i] = true;
                            File = new TFileStream(FileName, fmOpenRead | fmShareCompat);
                            if(iCodeIndex == 5 || iCodeIndex == 6)
                            {
                                if(((File->Size - 16) % DataSize) == 0)
                                    FontKindBlockCnt[iCodeIndex] = (File->Size - 16) / DataSize;
                                else
                                    FontKindBlockCnt[iCodeIndex] = (File->Size - 16) / DataSize + 1;
                                if(iCodeIndex == 5)
                                {
                                    //FontKindBlockCnt[iCodeIndex] = 12;
                                    if(FontKindBlockCnt[iCodeIndex] > 12)
                                        FontKindBlockCnt[iCodeIndex] = 12;
                                }
                                else if(iCodeIndex == 6)
                                {
                                    //FontKindBlockCnt[iCodeIndex] = 4;
                                    if(ComboBox2->ItemIndex == 1)
                                    {
                                        if(FontKindBlockCnt[iCodeIndex] > 8)
                                            FontKindBlockCnt[iCodeIndex] = 8;
                                    }
                                }
                            }
                            else if(iCodeIndex == 7)
                                TotPacketCnt += 2;
                            if(iCodeIndex == 1)
                            {
                                if((File->Size - 16) % DataSize)
                                    FontBlockCnt[j][i] = (File->Size - 16) / DataSize + 1;
                                else
                                    FontBlockCnt[j][i] = (File->Size - 16) / DataSize;
                                bAllSend[i] = true;
                            }
                            else if(iCodeIndex == 2)
                            {
                                if(File->Size < 2 * 1024 * 1024 - 2048)
                                {
                                    if((File->Size - 16) % DataSize)
                                        FontBlockCnt[j][i] = (File->Size - 16) / DataSize + 1;
                                    else
                                        FontBlockCnt[j][i] = (File->Size - 16) / DataSize;
                                    bAllSend[i] = true;
                                }
                                else
                                {
                                    if(ComboBox2->ItemIndex == 2)
                                        FontBlockCnt[j][i] = FontKindBlockCnt[iCodeIndex] * 4;
                                    else
                                        FontBlockCnt[j][i] = FontKindBlockCnt[iCodeIndex];
                                }
                            }
                            else
                                FontBlockCnt[j][i] = FontKindBlockCnt[iCodeIndex];
                            uSize.iSize = FontKindAddr[0][iCodeIndex];
                            if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                FontStartAddr[j][i] = uSize.iSize;
                            if(iCodeIndex == 1 && ComboBox2->ItemIndex >= 2)
                                uSize.iSize = 0x20;
                            FontData.FontSData[j].FontAddr[i].StartAddr[0] = uSize.cSize[0];
                            FontData.FontSData[j].FontAddr[i].StartAddr[1] = uSize.cSize[1];
                            uSize.iSize = FontKindAddr[1][iCodeIndex];
                            if(j == 0 && iCodeIndex == 1)
                            {
                                FontData.FontSData[j].FontAddr[i].EndAddr[0] = 0xff;
                                FontData.FontSData[j].FontAddr[i].EndAddr[1] = 0x00;
                                //FontKindBlockCnt[iCodeIndex] = 4096 / DataSize;
                                //FontBlockCnt[j][i] = FontKindBlockCnt[iCodeIndex];
                            }
                            else
                            {
                                FontData.FontSData[j].FontAddr[i].EndAddr[0] = uSize.cSize[0];
                                FontData.FontSData[j].FontAddr[i].EndAddr[1] = uSize.cSize[1];
                            }
                            TotPacketCnt += FontBlockCnt[j][i];
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                }
            }
            unsigned long ReadCnt = 0;
            NData = new BYTE[TotPacketCnt * DataSize];
            ZeroMemory(NData, TotPacketCnt * DataSize);
            TotPacketCnt = 0;
            TEdit *Edit2;
            for(int j = 0; j < iFontCount; j++)
            {
                bool bUniCodeFont = false;
                for(int i = 0; i < 3; i++)
                {
                    Edit2 = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                    if(Edit2 && FileSend[j][i])
                    {
                        FileName = Edit2->Text;
                        if(ExtractFileName(FileName) != "" && ExtractFileName(FileName) != " ")
                        {
                            if(FileExists(FileName))
                            {
                                bAllSend[i] = false;
                                File = new TFileStream(FileName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) +IntToStr(i + 1)));
                                ComboBox2 = dynamic_cast<TTntComboBox*>(FindComponent("cbFontSize" + IntToStr(j + 1)));
                                iCodeIndex = FindCodeIndex(ComboBox->Text);
                                if(iCodeIndex == 1)
                                    bAllSend[i] = true;
                                else if(iCodeIndex == 2)
                                {
                                    if(File->Size < 2 * 1024 * 1024 - 2048)
                                        bAllSend[i] = true;
                                }
                                if(bAllSend[i])
                                    File->Seek(16, soFromBeginning);
                                else
                                {
                                    if(FontData.FontSData[j].FontAddr[i].StartAddr[0] == 0x00 && FontData.FontSData[j].FontAddr[i].StartAddr[1] == 0xac ||
                                        FontData.FontSData[j].FontAddr[i].StartAddr[0] == 0x40 && FontData.FontSData[j].FontAddr[i].StartAddr[1] == 0x30 ||
                                        FontData.FontSData[j].FontAddr[i].StartAddr[0] == 0x00 && FontData.FontSData[j].FontAddr[i].StartAddr[1] == 0x4e)
                                    {
                                        if(ComboBox2->ItemIndex == 2)
                                            File->Seek(16 + (FontStartAddr[j][i] - 33) * 128, soFromBeginning);
                                        else
                                            File->Seek(16 + (FontStartAddr[j][i] - 33) * 32, soFromBeginning);
                                    }
                                    else
                                        File->Seek(16 + FontStartAddr[j][i] * 32, soFromBeginning);
                                }
                                if(FontData.FontSData[j].FontAddr[i].StartAddr[0] == 0x00 && FontData.FontSData[j].FontAddr[i].StartAddr[1] == 0x00 &&
                                    FontData.FontSData[j].FontAddr[i].EndAddr[0] == 0xa3 && FontData.FontSData[j].FontAddr[i].EndAddr[1] == 0xd7)
                                {
                                    ReadCnt += 1056;
                                    TotPacketCnt += 2;
                                }
                                if(j == 1)
                                {
                                    if(bAllSend[i])
                                    {
                                        File->Read(&NData[ReadCnt], File->Size - 16);
                                        if((File->Size - 16) % DataSize)
                                            FontBlockCnt[j][i] = (File->Size - 16) / DataSize + 1;
                                        else
                                            FontBlockCnt[j][i] = (File->Size - 16) / DataSize;
                                        ReadCnt = ReadCnt + FontBlockCnt[j][i] * DataSize;
                                    }
                                    else
                                    {
                                        if(File->Size - 16 < FontBlockCnt[j][i] * DataSize)
                                            File->Read(&NData[ReadCnt], File->Size - 16);
                                        else
                                            File->Read(&NData[ReadCnt], FontBlockCnt[j][i] * DataSize);
                                        //if(i == 0)
                                        //{
                                        //    if(File->Size > FontBlockCnt[j][i] * DataSize)
                                        //        ReadCnt = ReadCnt + File->Size;
                                        //    else
                                        //        ReadCnt = ReadCnt + FontBlockCnt[j][i] * DataSize;
                                        //}
                                        //else
                                            ReadCnt = ReadCnt + FontBlockCnt[j][i] * DataSize;
                                    }
                                }
                                else
                                {
                                    if(bAllSend[i])
                                    {
                                        File->Read(&NData[ReadCnt], File->Size - 16);
                                        if((File->Size - 16) % DataSize)
                                            FontBlockCnt[j][i] = (File->Size - 16) / DataSize + 1;
                                        else
                                            FontBlockCnt[j][i] = (File->Size - 16) / DataSize;
                                    }
                                    else
                                    {
                                        if(File->Size - 16 < FontBlockCnt[j][i] * DataSize)
                                            File->Read(&NData[ReadCnt], File->Size - 16);
                                        else
                                            File->Read(&NData[ReadCnt], FontBlockCnt[j][i] * DataSize);
                                    }
                                    ReadCnt = ReadCnt + FontBlockCnt[j][i] * DataSize;
                                }
                                if(FontData.FontSData[j].FontAddr[i].StartAddr[0] == 0x00 && FontData.FontSData[j].FontAddr[i].StartAddr[1] == 0x00 &&
                                    FontData.FontSData[j].FontAddr[i].EndAddr[0] == 0xa3 && FontData.FontSData[j].FontAddr[i].EndAddr[1] == 0xd7)
                                    ReadCnt += 992;
                                TotPacketCnt += FontBlockCnt[j][i];
                                if(File)
                                {
                                    delete File;
                                    File = NULL;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(...)
        {
            if(NData)
            {
                delete [] NData;
                NData = NULL;
            }
            if(File)
            {
                delete File;
                File = NULL;
            }
            TmpMsg = "Font Error";
            FormMain->Message("\r\n" + TmpMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", TmpMsg.Length() - 2, 3, 1, clRed);
            this->Enabled = true;
            BtnSend->Enabled = true;
            return false;
        }
        int TotPercent = 0.0;
        int TotalPacketPercent = 0.0;
        if(FormMain->FTransfer)
        {
            delete FormMain->FTransfer;
            FormMain->FTransfer = NULL;
        }
        FormMain->FTransfer = new TFormTransfer(this);
        FormMain->FTransfer->LabelSign->Visible = false;
        FormMain->FTransfer->Label6->Visible = false;
        FormMain->FTransfer->ProgressBar->Visible = false;
        FormMain->FTransfer->LabelTotPercent->Visible = false;
        FormMain->FTransfer->LabelCount->Caption = "Total : 1 / " + IntToStr(TotPacketCnt);
        FormMain->FTransfer->LabelTotalPacket->Caption = IntToStr((int)TotalPacketPercent) + AnsiString("%");
        FormMain->FTransfer->ProgressBar->Position = 0;
        FormMain->FTransfer->LabelName->Caption = "Font File Data";
        //FormMain->FTransfer->AnimateTransfer->Play(0, 31, 0);
        //FormMain->FTransfer->AnimateTransfer->Active = true;
        FormMain->FTransfer->Show();
        FormMain->FTransfer->BringToFront();
        BYTE *SendFrameData = NULL;
        SendFrameData = new BYTE[DataSize];
        int TotCnt = 0;
        FormMain->RetryCnt = 0;
        TmpMsg = "Font Image";
        try
        {
            for(int j = 0; j < iFontCount; j++)
            {
                TotBlockCnt = 0;
                if(FormMain->bStopSend)
                    break;
                for(int i = 0; i < 3; i++)
                {
                    if((FontData.FontSData[j].FontAddr[i].StartAddr[0] != 0x00) || (FontData.FontSData[j].FontAddr[i].StartAddr[1] != 0x00) ||
                        (FontData.FontSData[j].FontAddr[i].EndAddr[0] != 0x00) || (FontData.FontSData[j].FontAddr[i].EndAddr[1] != 0x00))
                    {
                        ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontSize" + IntToStr(j + 1)));
                        if(FontData.FontSData[j].FontAddr[i].EndAddr[0] == 0x7f && FontData.FontSData[j].FontAddr[i].EndAddr[1] == 0x00 ||
                            FontData.FontSData[j].FontAddr[i].EndAddr[0] == 0xff && FontData.FontSData[j].FontAddr[i].EndAddr[1] == 0x00)
                        {
                            //uSize.iSize = TotBlockCnt * DataSize / 16;
                            if(ComboBox)
                            {
                                if(ComboBox->ItemIndex == 2)
                                {
                                    FontData.FontSData[j].FontAddr[i].Width = 16;
                                    FontData.FontSData[j].FontAddr[i].Height = 32;
                                }
                                else if(ComboBox->ItemIndex == 3)
                                {
                                    FontData.FontSData[j].FontAddr[i].Width = 24;
                                    FontData.FontSData[j].FontAddr[i].Height = 48;
                                }
                                else if(ComboBox->ItemIndex == 4)
                                {
                                    FontData.FontSData[j].FontAddr[i].Width = 32;
                                    FontData.FontSData[j].FontAddr[i].Height = 64;
                                }
                                else if(ComboBox->ItemIndex == 5)
                                {
                                    FontData.FontSData[j].FontAddr[i].Width = 16;
                                    FontData.FontSData[j].FontAddr[i].Height = 16;
                                }
                                else
                                {
                                    FontData.FontSData[j].FontAddr[i].Width = 8;
                                    FontData.FontSData[j].FontAddr[i].Height = 16;
                                }
                            }
                            else
                            {
                                FontData.FontSData[j].FontAddr[i].Width = 8;
                                FontData.FontSData[j].FontAddr[i].Height = 16;
                            }
                        }
                        else
                        {
                            //uSize.iSize = TotBlockCnt * DataSize / 32;
                            if(ComboBox)
                            {
                                if(ComboBox->ItemIndex == 2)
                                {
                                    FontData.FontSData[j].FontAddr[i].Width = 32;
                                    FontData.FontSData[j].FontAddr[i].Height = 32;
                                }
                                else
                                {
                                    FontData.FontSData[j].FontAddr[i].Width = 16;
                                    FontData.FontSData[j].FontAddr[i].Height = 16;
                                }
                            }
                            else
                            {
                                FontData.FontSData[j].FontAddr[i].Width = 16;
                                FontData.FontSData[j].FontAddr[i].Height = 16;
                            }
                        }
                        uSize.iSize = TotBlockCnt;
                        FontData.FontSData[j].FontAddr[i].FontPos[0] = uSize.cSize[0];
                        FontData.FontSData[j].FontAddr[i].FontPos[1] = uSize.cSize[1];
                        TotBlockCnt += FontBlockCnt[j][i];
                        if(FontData.FontSData[j].FontAddr[i].StartAddr[0] == 0x00 && FontData.FontSData[j].FontAddr[i].StartAddr[1] == 0x00 &&
                            FontData.FontSData[j].FontAddr[i].EndAddr[0] == 0xa3 && FontData.FontSData[j].FontAddr[i].EndAddr[1] == 0xd7)
                                TotBlockCnt += 2;
                    }
                }
                uSize.iSize = TotBlockCnt * DataSize;
                FontData.FontSData[j].FontByteCnt[0] = uSize.cSize[0];
                FontData.FontSData[j].FontByteCnt[1] = uSize.cSize[1];
                FontData.FontSData[j].FontByteCnt[2] = uSize.cSize[2];
                FontData.FontSData[j].FontByteCnt[3] = uSize.cSize[3];
                FormMain->RetryCnt = 0;
                for(int x = 0; x < TotBlockCnt; x++)
                {
                    if(FormMain->bStopSend)
                        break;
                    ZeroMemory(SendFrameData, DataSize);
                    memcpy(SendFrameData, &NData[TotCnt * DataSize], DataSize);
                    Connect = FormMain->ModuleFontPacketData(ComKind, Addr, FFNT, SendFrameData, TotPacketCnt, TotCnt, TotBlockCnt, x, 0, j + 1);
                    if(Connect)
                    {
                        TotCnt++;
                        //if(x <= 1)
                            FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                        PacketCnt++;
                        TotalPacketPercent = ((PacketCnt) * 100 / ((double)TotPacketCnt));
                        FormMain->RetryCnt = 0;
                        if(FormMain->FTransfer)
                        {
                            FormMain->FTransfer->ProgressBar1->Position = TotalPacketPercent;
                            FormMain->FTransfer->ProgressBar1->Update();
                            FormMain->FTransfer->LabelTotalPacket->Caption = IntToStr((int)TotalPacketPercent) + AnsiString("%");
                            FormMain->FTransfer->Show();
                            FormMain->FTransfer->Update();
                            FormMain->FTransfer->BringToFront();
                        }
                    }
                    else
                    {
                        if(FormMain->bCRCFail || FormMain->bDataFail)
                        {
                            FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
                            FormMain->bStopSend = true;
                            break;
                        }
                        else
                        {
                            if(FormMain->RetryCnt < 3 - 1)
                            {
                                FormMain->RetryCnt++;
                                FormMain->AddLog("Fail Transmission Count : " + IntToStr(FormMain->RetryCnt), clRed);
                                FormMain->SchBmpFile = false;
                                FormMain->DelayCount(FormMain->LanDelayTime);
                                x -= 1;
                                continue;
                            }
                            else
                            {
                                TmpMsg = "Transmission failure";
                                FormMain->Message("\r\n" + TmpMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", TmpMsg.Length() - 2, 3, 1, clBlack);
                                FormMain->AddLog(TmpMsg + " FAIL]" + FormMain->asLogMessage, clRed);
                                FormMain->bStopSend = true;
                                break;
                            }
                        }
                    }
                }
                TmpMsg = "Font Image";
                if(Connect && j == iFontCount - 1)
                {
                    ZeroMemory(SendFrameData, DataSize);
                    int tempSize = 0;
                    if(iFontCount > 2)
                        tempSize = sizeof(sFontSData) * iFontCount + 1;
                    else
                        tempSize = sizeof(sFontSData) * 2 + 1;
                    memcpy(SendFrameData, &FontData, tempSize);
                    Connect = FormMain->ModuleData(ComKind, Addr, SendFrameData, tempSize, 0, ED, false);
                    if(Connect)
                    {
                        FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                        TotalPacketPercent = ((PacketCnt) * 100 / ((double)TotPacketCnt));
                        if(FormMain->FTransfer)
                        {
                            FormMain->FTransfer->ProgressBar1->Position = TotalPacketPercent;
                            FormMain->FTransfer->ProgressBar1->Update();
                            FormMain->FTransfer->LabelTotalPacket->Caption = IntToStr((int)TotalPacketPercent) + AnsiString("%");
                            FormMain->FTransfer->Show();
                            FormMain->FTransfer->Update();
                            FormMain->FTransfer->BringToFront();
                        }
                    }
                    else
                    {
                        TmpMsg = "Transmission failure";
                        FormMain->Message("\r\n" + TmpMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", TmpMsg.Length() - 2, 3, 1, clBlack);
                        FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
                    }
                }
            }
        }catch(...){
        }
        AnsiString ParamFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "nfont_00.fnt";
        TFileStream *FontFile = NULL;
        try{
            FontFile = new TFileStream(ParamFileName, fmCreate | fmShareCompat);
            FontFile->Write(NData, TotPacketCnt * DataSize);
        }catch(...){
        }
        if(FontFile)
        {
            delete FontFile;
            FontFile = NULL;
        }
        if(SendFrameData)
        {
            delete [] SendFrameData;
            SendFrameData = NULL;
        }
        if(NData)
        {
            delete [] NData;
            NData = NULL;
        }
        if(FormMain->FTransfer)
        {
            FormMain->FTransfer->LabelCount->Caption = "Total : " + IntToStr(TotPacketCnt) + " / " + IntToStr(TotPacketCnt);
            TotPercent = (double)100;
            TotalPacketPercent = (double)100;
            FormMain->FTransfer->ProgressBar1->Position = TotPercent;
            FormMain->FTransfer->ProgressBar1->Update();
            FormMain->FTransfer->LabelTotPercent->Caption = IntToStr((int)TotPercent) + AnsiString("%");
            FormMain->FTransfer->Show();
            FormMain->FTransfer->Update();
            FormMain->FTransfer->BringToFront();
            Sleep(100);
            FormMain->FTransfer->Close();
            delete FormMain->FTransfer;
            FormMain->FTransfer = NULL;
        }
        return Connect;
}
//---------------------------------------------------------------------------

int __fastcall TFormFont::FindCodeIndex(AnsiString CodeName)
{
        WideString wsCodeNames[8];
        wsCodeNames[0] = FormMain->LangFile.FormProtocolEffect1;
        for(int i = 0; i < 7; i++)
            wsCodeNames[i + 1] = FormMain->LangFile.FormFontItem[i];
        for(int i = 0; i < 8; i++)
        {
            if(!CodeName.AnsiCompare(AnsiString(wsCodeNames[i])))
                return i;
        }
        return 0;
}
//---------------------------------------------------------------------------

WideString __fastcall TFormFont::FindCodeIndexString(int iCodeIndex)
{
        WideString wsCodeNames[8];
        wsCodeNames[0] = FormMain->LangFile.FormProtocolEffect1;
        for(int i = 0; i < 7; i++)
            wsCodeNames[i + 1] = FormMain->LangFile.FormFontItem[i];
        return wsCodeNames[iCodeIndex];
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind12Click(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind12->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind13->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind13->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4)
        {
            IndexValue = FindCodeIndex(cbFontKind13->Text);
            if(IndexValue == 5 || IndexValue == 7)
                cbFontKind13->ItemIndex = 0;
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind13Ciick(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind13->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind12->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind12->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
        {
            if(IndexValue == 7)
            {
                IndexValue = FindCodeIndex(cbFontKind12->Text);
                if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 5)
                    cbFontKind12->ItemIndex = 0;
            }
            else
            {
                IndexValue = FindCodeIndex(cbFontKind12->Text);
                if(IndexValue == 5)
                    cbFontKind12->ItemIndex = 0;
            }
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------


void __fastcall TFormFont::cbFontKind22Ciick(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind22->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind23->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind23->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4)
        {
            IndexValue = FindCodeIndex(cbFontKind23->Text);
            if(IndexValue == 5 || IndexValue == 7)
                cbFontKind23->ItemIndex = 0;
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind23Ciick(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind23->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind22->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind22->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
        {
            if(IndexValue == 7)
            {
                IndexValue = FindCodeIndex(cbFontKind22->Text);
                if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 5)
                    cbFontKind22->ItemIndex = 0;
            }
            else
            {
                IndexValue = FindCodeIndex(cbFontKind22->Text);
                if(IndexValue == 5)
                    cbFontKind22->ItemIndex = 0;
            }
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFont2Click(TObject *Sender)
{
        laFontLabel6->Enabled = cbFont2->Checked;
        laFontLabel3->Enabled = cbFont2->Checked;
        cbFontKind21->Enabled = cbFont2->Checked;
        eFont21->Enabled = cbFont2->Checked;
        sbPathFont21->Enabled = cbFont2->Checked;
        cbFontKind22->Enabled = cbFont2->Checked;
        eFont22->Enabled = cbFont2->Checked;
        sbPathFont22->Enabled = cbFont2->Checked;
        cbFontKind23->Enabled = cbFont2->Checked;
        eFont23->Enabled = cbFont2->Checked;
        sbPathFont23->Enabled = cbFont2->Checked;
        cbFontSize2->Enabled  = cbFont2->Checked;
        cbFontSize3->Enabled  = cbFont3->Checked;
        cbFontSize4->Enabled  = cbFont4->Checked;
        if(!cbFont2->Checked)
        {
            cbFont3->Checked = false;
            cbFont3->Enabled = false;
            cbFont4->Checked = false;
            cbFont4->Enabled = false;
        }
        else
            cbFont3->Enabled = true;
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind32Ciick(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind32->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind33->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind33->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4)
        {
            IndexValue = FindCodeIndex(cbFontKind33->Text);
            if(IndexValue == 5 || IndexValue == 7)
                cbFontKind33->ItemIndex = 0;
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind33Ciick(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind33->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind32->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind32->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
        {
            if(IndexValue == 7)
            {
                IndexValue = FindCodeIndex(cbFontKind32->Text);
                if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 5)
                    cbFontKind32->ItemIndex = 0;
            }
            else
            {
                IndexValue = FindCodeIndex(cbFontKind32->Text);
                if(IndexValue == 5)
                    cbFontKind32->ItemIndex = 0;
            }
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind42Ciick(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind42->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind43->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind43->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4)
        {
            IndexValue = FindCodeIndex(cbFontKind43->Text);
            if(IndexValue == 5 || IndexValue == 7)
                cbFontKind43->ItemIndex = 0;
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind43Ciick(TObject *Sender)
{
        int IndexValue = 0;
        IndexValue = FindCodeIndex(cbFontKind43->Text);
        if(IndexValue == 5)
        {
            IndexValue = FindCodeIndex(cbFontKind42->Text);
            if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
                cbFontKind42->ItemIndex = 0;
        }
        else if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 7)
        {
            if(IndexValue == 7)
            {
                IndexValue = FindCodeIndex(cbFontKind42->Text);
                if(IndexValue == 2 || IndexValue == 3 || IndexValue == 4 || IndexValue == 5)
                    cbFontKind42->ItemIndex = 0;
            }
            else
            {
                IndexValue = FindCodeIndex(cbFontKind42->Text);
                if(IndexValue == 5)
                    cbFontKind42->ItemIndex = 0;
            }
        }
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFont3Click(TObject *Sender)
{
        laFontLabel7->Enabled = cbFont3->Checked;
        laFontLabel8->Enabled = cbFont3->Checked;
        cbFontKind31->Enabled = cbFont3->Checked;
        eFont31->Enabled      = cbFont3->Checked;
        sbPathFont31->Enabled = cbFont3->Checked;
        cbFontKind32->Enabled = cbFont3->Checked;
        eFont32->Enabled      = cbFont3->Checked;
        sbPathFont32->Enabled = cbFont3->Checked;
        cbFontKind33->Enabled = cbFont3->Checked;
        eFont33->Enabled      = cbFont3->Checked;
        sbPathFont33->Enabled = cbFont3->Checked;
        cbFontSize2->Enabled  = cbFont2->Checked;
        cbFontSize3->Enabled  = cbFont3->Checked;
        cbFontSize4->Enabled  = cbFont4->Checked;
        if(!cbFont3->Checked)
        {
            cbFont4->Checked = false;
            cbFont4->Enabled = false;
        }
        else
            cbFont4->Enabled = true;
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFont4Click(TObject *Sender)
{
        laFontLabel9->Enabled = cbFont4->Checked;
        laFontLabel10->Enabled = cbFont4->Checked;
        cbFontKind41->Enabled = cbFont4->Checked;
        eFont41->Enabled      = cbFont4->Checked;
        sbPathFont41->Enabled = cbFont4->Checked;
        cbFontKind42->Enabled = cbFont4->Checked;
        eFont42->Enabled      = cbFont4->Checked;
        sbPathFont42->Enabled = cbFont4->Checked;
        cbFontKind43->Enabled = cbFont4->Checked;
        eFont43->Enabled      = cbFont4->Checked;
        sbPathFont43->Enabled = cbFont4->Checked;
        cbFontSize2->Enabled  = cbFont2->Checked;
        cbFontSize3->Enabled  = cbFont3->Checked;
        cbFontSize4->Enabled  = cbFont4->Checked;
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFontKind21Click(TObject *Sender)
{
        TEdit *Edit;
        TTntLabel *Label;
        TTntComboBox *ComboBox;
        int iTotFontSize = 0;
        bool bChecked = false;
        TTntCheckBox *CheckBox;
        for(int j = 0; j < 4; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                if(j == 0)
                    bChecked = true;
                else
                {
                    CheckBox = dynamic_cast<TTntCheckBox*>(FindComponent("cbFont" + IntToStr(j + 1)));
                    if(CheckBox)
                        bChecked = CheckBox->Checked;
                }
                Edit = dynamic_cast<TEdit*>(FindComponent("eFont" + IntToStr(j + 1) + IntToStr(i + 1)));
                if(Edit)
                {
                    AnsiString schName = Edit->Text;
                    if(FileExists(schName))
                    {
                        Label = dynamic_cast<TTntLabel*>(FindComponent("laFontSize" + IntToStr(j + 1) + IntToStr(i + 1)));
                        if(Label)
                        {
                            TFileStream *File = NULL;
                            try{
                                File = new TFileStream(schName, fmOpenRead | fmShareCompat);
                                ComboBox = dynamic_cast<TTntComboBox*>(FindComponent("cbFontKind" + IntToStr(j + 1) + IntToStr(i + 1)));
                                if(ComboBox)
                                {
                                    if(ComboBox->ItemIndex != 0)
                                    {
                                        int iCodeIndex = FindCodeIndex(ComboBox->Text);
                                        if(iCodeIndex == 2 || iCodeIndex == 3 || iCodeIndex == 4)
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + ((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32;
                                            Label->Caption = IntToStr(((FontKindAddr[1][iCodeIndex] - FontKindAddr[0][iCodeIndex]) + 1) * 32) + " Byte";
                                        }
                                        else
                                        {
                                            if(bChecked)
                                                iTotFontSize = iTotFontSize + File->Size - 16;
                                            Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                        }
                                    }
                                    else
                                        Label->Caption = "0 Byte";
                                }
                                else
                                {
                                    if(bChecked)
                                        iTotFontSize = iTotFontSize + File->Size - 16;
                                    Label->Caption = IntToStr(File->Size - 16) + " Byte";
                                }
                            }catch(...){
                            }
                            if(File)
                            {
                                delete File;
                                File = NULL;
                            }
                        }
                    }
                    else
                        Edit->Text = "";
                }
            }
        }
        laTotFontSize->Caption = "FONT SIZE(Total/Limit):\r\n" + IntToStr(iTotFontSize) + " / 3145727 Byte";
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::cbFont1Click(TObject *Sender)
{
        cbFont1->Checked = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormFont::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormFont::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormFont::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------
