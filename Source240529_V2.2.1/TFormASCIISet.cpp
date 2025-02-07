//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormASCIISet.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "te_controls"
#pragma link "TntButtons"
#pragma link "TntStdCtrls"
#pragma link "TntExtCtrls"
#pragma resource "*.dfm"
TFormASCIISet *FormASCIISet;
//---------------------------------------------------------------------------
__fastcall TFormASCIISet::TFormASCIISet(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption = FormMain->LangFile.FormASCIISet;
        pnCaption->Caption = " " + this->Caption;
        Label1->Caption     = FormMain->LangFile.FormProtocolLabel1;
        Label2->Caption     = FormMain->LangFile.FormProtocolLabel2;
        Label3->Caption     = FormMain->LangFile.FormProtocolLabel3;
        Label4->Caption     = FormMain->LangFile.FormProtocolLabel4;
        Label5->Caption     = FormMain->LangFile.FormProtocolLabel5;
        Label6->Caption     = FormMain->LangFile.FormProtocolLabel6 + "(Pixel)";
        Label21->Caption    = FormMain->LangFile.FormFontLabel6;
        Label7->Caption     = FormMain->LangFile.FormProtocolLabel7;
        Label8->Caption     = FormMain->LangFile.FormProtocolLabel8;
        Label9->Caption     = FormMain->LangFile.FormProtocolLabel9;
        Label10->Caption    = FormMain->LangFile.FormProtocolLabel10;
        Label11->Caption    = FormMain->LangFile.FormProtocolLabel11;
        Label12->Caption    = FormMain->LangFile.FormProtocolLabel12 + "(Pixel)";
        Label13->Caption    = FormMain->LangFile.FormProtocolLabel13 + "(Pixel)";
        Label14->Caption    = FormMain->LangFile.FormProtocolLabel14 + "(Pixel)";
        Label15->Caption    = FormMain->LangFile.FormProtocolLabel15 + "(Pixel)";
        Label16->Caption    = FormMain->LangFile.FormProtocolLabel16;
        Label17->Caption    = FormMain->LangFile.FormProtocolLabel17.SubString(1, FormMain->LangFile.FormProtocolLabel17.Pos("(") - 1);
        Label18->Caption    = FormMain->LangFile.FormProtocolLabel18.SubString(1, FormMain->LangFile.FormProtocolLabel18.Pos("(") - 1);
        Label23->Caption    = FormMain->LangFile.FormProtocolEffect11;
        Label24->Caption    = FormMain->LangFile.FormProtocolEffect11;
        btnSet->Caption     = FormMain->LangFile.MessageButton17;
        btnSend->Caption    = FormMain->LangFile.BtnSend;
        btnRead->Caption    = FormMain->LangFile.MessageButton15;
        btnFactoryReset->Caption = FormMain->LangFile.BtnFReset;
        BitBtnOk->Caption   = FormMain->LangFile.MessageButton10;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        rgPageNo->Color = FormMain->MainRectangleColor;
        pnPageNo->Color = FormMain->MainColor;
        rbPageNo1->Color = FormMain->MainColor;
        rbPageNo1->Font->Color = FormMain->TextColor;
        rbPageNo1->Font->Color = FormMain->TextColor;
        rbPageNo1->Font->Name = FormMain->MainFont;
        rbPageNo1->Font->Size = FormMain->MainFontSize;
        rbPageNo2->Color = FormMain->MainColor;
        rbPageNo2->Font->Color = FormMain->TextColor;
        rbPageNo2->Font->Name = FormMain->MainFont;
        rbPageNo2->Font->Size = FormMain->MainFontSize;
        rbTextNo->Color = FormMain->MainRectangleColor;
        pnTextNo->Color = FormMain->MainColor;
        rbNo1->Color = FormMain->MainColor;
        rbNo1->Font->Color = FormMain->TextColor;
        rbNo1->Font->Name = FormMain->MainFont;
        rbNo1->Font->Size = FormMain->MainFontSize;
        rbNo2->Color = FormMain->MainColor;
        rbNo2->Font->Color = FormMain->TextColor;
        rbNo2->Font->Name = FormMain->MainFont;
        rbNo2->Font->Size = FormMain->MainFontSize;
        rbNo3->Color = FormMain->MainColor;
        rbNo3->Font->Color = FormMain->TextColor;
        rbNo3->Font->Name = FormMain->MainFont;
        rbNo3->Font->Size = FormMain->MainFontSize;
        mASCIIText11->Color = FormMain->TextColor;
        mASCIIText11->Font->Color = (TColor)RGB(245, 245, 245);
        mASCIIText11->Font->Name = FormMain->MainFont;
        mASCIIText11->Font->Size = 11;
        TTntLabel *tLabel;
        try{
            for(int i = 0; i < 24; i++)
            {
                tLabel = dynamic_cast<TTntLabel *>(FindComponent("Label" + IntToStr(i + 1)));
                if(tLabel)
                {
                    tLabel->Font->Color = FormMain->TextColor;
                    tLabel->Font->Name = FormMain->MainFont;
                    tLabel->Font->Size = FormMain->MainFontSize;
                }
            }
        }catch(...){
        }
        cbDispControl->Font->Name = FormMain->MainFont;
        cbDispControl->Font->Size = FormMain->MainFontSize;
        cbDispType->Font->Name = FormMain->MainFont;
        cbDispType->Font->Size = FormMain->MainFontSize;
        cbCodeKind->Font->Name = FormMain->MainFont;
        cbCodeKind->Font->Size = FormMain->MainFontSize;
        cbFontSize->Font->Name = FormMain->MainFont;
        cbFontSize->Font->Size = FormMain->MainFontSize;
        cbEntEffect->Font->Name = FormMain->MainFont;
        cbEntEffect->Font->Size = FormMain->MainFontSize;
        cbLasEffect->Font->Name = FormMain->MainFont;
        cbLasEffect->Font->Size = FormMain->MainFontSize;
        cbAssistEffect->Font->Name = FormMain->MainFont;
        cbAssistEffect->Font->Size = FormMain->MainFontSize;
        cbSpeed->Font->Name = FormMain->MainFont;
        cbSpeed->Font->Size = FormMain->MainFontSize;
        cbFontKind->Font->Name = FormMain->MainFont;
        cbFontKind->Font->Size = FormMain->MainFontSize;
        cbEntDirect->Font->Name = FormMain->MainFont;
        cbEntDirect->Font->Size = FormMain->MainFontSize;
        cbLasDirect->Font->Name = FormMain->MainFont;
        cbLasDirect->Font->Size = FormMain->MainFontSize;
        cbDelay->Font->Name = FormMain->MainFont;
        cbDelay->Font->Size = FormMain->MainFontSize;
        cbStartXPos->Font->Name = FormMain->MainFont;
        cbStartXPos->Font->Size = FormMain->MainFontSize;
        cbStartYPos->Font->Name = FormMain->MainFont;
        cbStartYPos->Font->Size = FormMain->MainFontSize;
        cbEndXPos->Font->Name = FormMain->MainFont;
        cbEndXPos->Font->Size = FormMain->MainFontSize;
        cbEndYPos->Font->Name = FormMain->MainFont;
        cbEndYPos->Font->Size = FormMain->MainFontSize;
        cbBGImage->Font->Name = FormMain->MainFont;
        cbBGImage->Font->Size = FormMain->MainFontSize;
        btnSet->Font->Color = FormMain->TextBGColor;
        btnSet->Font->Name = FormMain->MainFont;
        btnSet->Font->Size = FormMain->MainFontSize;
        pnbtnSet->Color = FormMain->ButtonColor;
        btnSend->Font->Color = FormMain->TextBGColor;
        btnSend->Font->Name = FormMain->MainFont;
        btnSend->Font->Size = FormMain->MainFontSize;
        pnbtnSend->Color = FormMain->ButtonColor;
        btnRead->Font->Color = FormMain->TextBGColor;
        btnRead->Font->Name = FormMain->MainFont;
        btnRead->Font->Size = FormMain->MainFontSize;
        pnbtnRead->Color = FormMain->ButtonColor;
        btnFactoryReset->Font->Color = FormMain->TextBGColor;
        btnFactoryReset->Font->Name = FormMain->MainFont;
        btnFactoryReset->Font->Size = FormMain->MainFontSize;
        pnbtnFactoryReset->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::FormCreate(TObject *Sender)
{
        cbDispControl->Clear();
        cbDispControl->ItemIndex = -1;
        cbDispControl->Items->Add("OFF");
        cbDispControl->Items->Add("ON");
        for(int i = 1; i <= 10; i++)
        {
            cbDispControl->Items->Add(IntToStr(i));
        }
        cbDispControl->Items->Add("20");
        cbDispControl->Items->Add("30");
        cbDispControl->Items->Add("40");
        cbDispControl->Items->Add("50");
        cbDispControl->Items->Add("60");
        cbDispControl->Items->Add("70");
        cbDispControl->Items->Add("80");
        cbDispControl->Items->Add("90");
        cbDispControl->ItemIndex = 0;

        rbPageNo1->Caption = FormMain->LangFile.FormProtocolLabel28;
        rbPageNo2->Caption = FormMain->LangFile.FormProtocolLabel29;

        cbAssistEffect->Clear();
        cbAssistEffect->Items->Add(FormMain->LangFile.FormProtocolEffect1);
        cbAssistEffect->ItemIndex = 0;

        cbCodeKind->Clear();
        cbCodeKind->Items->Add(FormMain->LangFile.FormProtocolEffect5);
        cbCodeKind->Items->Add(FormMain->LangFile.FormProtocolEffect6);
        cbCodeKind->ItemIndex = 0;

        cbFontSize->Items->Clear();
        for(int i = 4; i <= 16; i++)
        {
            if(i == 4)
                cbFontSize->Items->Add(IntToStr(i * 4) + "(Standard)");
            else
                cbFontSize->Items->Add(IntToStr(i * 4));
        }
        cbFontSize->ItemIndex = 0;
        cbFontKind->ItemIndex = 0;

        cbEntEffect->Clear();
        cbLasEffect->Clear();
        cbLasEffect->Items->Add(FormMain->LangFile.NoEffect);
        for(int i = 0; i < EffectTypeCount - 1; i++)
        {
            if(i == etScaleIn)
                continue;
            cbEntEffect->Items->Add(FormMain->EffectType[i]);
            cbLasEffect->Items->Add(FormMain->EffectType[i]);
        }
        cbEntEffect->ItemIndex = 0;
        cbLasEffect->ItemIndex = 0;
        cbEntDirect->Text = FormMain->DirType[0];
        cbLasDirect->Text = FormMain->LangFile.NoEffect;

        cbSpeed->Clear();
        FormMain->SpeedValue[1] = "5(" + FormMain->LangFile.Message01 + ")";
        FormMain->SpeedValue[14] = IntToStr(99) + "(" + FormMain->LangFile.Message02 + ")";
        for(int i = 1; i < 15; i++)
        {
            cbSpeed->Items->Add(FormMain->SpeedValue[i]);
        }
        cbSpeed->ItemIndex = 0;

        cbDelay->Clear();
        for(int i = 0; i < 20; i++)
        {
            cbDelay->Items->Add(FormMain->DelayValue[i]);
        }
        cbDelay->ItemIndex = 1;

        cbStartXPos->ItemIndex = -1;
        cbEndXPos->ItemIndex = -1;
        cbStartXPos->Clear();
        cbEndXPos->Clear();
        for(int i = 0; i <= FormMain->ModuleWidth * 4; i++)
        {
            cbStartXPos->Items->Add(IntToStr(i * 4));
            cbEndXPos->Items->Add(IntToStr(i * 4));
        }
        cbStartXPos->ItemIndex = 0;
        cbEndXPos->ItemIndex = cbEndXPos->Items->Count - 1;

        cbStartYPos->ItemIndex = -1;
        cbEndYPos->ItemIndex = -1;
        cbStartYPos->Clear();
        cbEndYPos->Clear();
        for(int i = 0; i <= FormMain->ModuleHeight * 4; i++)
        {
            cbStartYPos->Items->Add(IntToStr(i * 4));
            cbEndYPos->Items->Add(IntToStr(i * 4));
        }
        cbStartYPos->ItemIndex = 0;
        cbEndYPos->ItemIndex = cbEndYPos->Items->Count - 1;

        cbBGImage->Clear();
        cbBGImage->Items->Add(FormMain->LangFile.FormProtocolEffect1);
        for(int i = 1; i <= 10; i++)
        {
            cbBGImage->Items->Add(IntToStr(i));
        }
        cbBGImage->ItemIndex = 0;

        AnsiString asData = AnsiString((char*)FormMain->ASCIIMessageData[10].MessageData).SubString(1, sizeof(FormMain->ASCIIMessageData[0].MessageData));
        mASCIIText11->Text = asData;
        if(FormMain->ASCIIPageNo >= 1)
            rbPageNo2->Checked = true;
        else
            rbPageNo1->Checked = true;
        if(FormMain->ASCIIBlockNo == 1)
            rbNo2->Checked = true;
        else if(FormMain->ASCIIBlockNo == 2)
            rbNo3->Checked = true;
        else// if(FormMain->ASCIIBlockNo == 0)
            rbNo1->Checked = true;
        rbASCIINoRoadClick(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::rbASCIINoRoadClick(TObject *Sender)
{
        // 긴급문구 구조체 저장 함수
        try{
            ProtocolPageNo = 11;
            FormMain->ASCIIPageNo = rbPageNo2->Checked;
            cbDispControl->Clear();
            cbDispControl->ItemIndex = -1;
            cbDispControl->Items->Add("OFF");
            cbDispControl->Items->Add("ON");
            if(rbNo1->Checked)
                FormMain->ASCIIBlockNo = 0;
            else if(rbNo2->Checked)
                FormMain->ASCIIBlockNo = 1;
            else if(rbNo3->Checked)
                FormMain->ASCIIBlockNo = 2;
            if(rbPageNo1->Checked)
            {
                for(int i = 1; i <= 10; i++)
                {
                    cbDispControl->Items->Add(IntToStr(i));
                }
                cbDispControl->Items->Add("20");
                cbDispControl->Items->Add("30");
                cbDispControl->Items->Add("40");
                cbDispControl->Items->Add("50");
                cbDispControl->Items->Add("60");
                cbDispControl->Items->Add("70");
                cbDispControl->Items->Add("80");
                cbDispControl->Items->Add("90");
            }
            union{
                char cbSize;
                struct{
                    unsigned bit0 : 7;
                    unsigned bit1 : 1;
                }Bit;
            }uSize3;
            uSize3.cbSize = FormMain->ProtocolData.ProtocolDispControl[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbDispControl->ItemIndex = uSize3.Bit.bit0;
            cbDispType->ItemIndex = FormMain->ProtocolData.ProtocolDispType[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbCodeKind->ItemIndex = FormMain->ProtocolData.ProtocolCodeKind[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbFontSize->ItemIndex = FormMain->ProtocolData.ProtocolFontSize[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbFontKind->ItemIndex = FormMain->ProtocolData.ProtocolFontKind[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbFontSizeClick(this);
            cbEntEffect->ItemIndex = -1;
            cbEntEffect->ItemIndex = cbEntEffect->Items->IndexOf(AnsiString((char*)FormMain->ProtocolData.ProtocolEntEffect[ProtocolPageNo][FormMain->ASCIIBlockNo]).SubString(1, sizeof(FormMain->ProtocolData.ProtocolEntEffect[ProtocolPageNo][ProtocolPageNo])));
            if(cbEntEffect->ItemIndex < 0)
                cbEntEffect->ItemIndex = 0;
            cbEntDirectDropDown(this);
            cbEntDirect->ItemIndex = cbEntDirect->Items->IndexOf(AnsiString((char*)FormMain->ProtocolData.ProtocolEntDirect[ProtocolPageNo][FormMain->ASCIIBlockNo]).SubString(1, sizeof(FormMain->ProtocolData.ProtocolEntDirect[ProtocolPageNo][ProtocolPageNo])));
            if(cbEntDirect->ItemIndex < 0)
                cbEntDirect->ItemIndex = 0;
            cbLasEffect->ItemIndex = - 1;
            cbLasEffect->ItemIndex = cbLasEffect->Items->IndexOf(AnsiString((char*)FormMain->ProtocolData.ProtocolLasEffect[ProtocolPageNo][FormMain->ASCIIBlockNo]).SubString(1, sizeof(FormMain->ProtocolData.ProtocolLasEffect[ProtocolPageNo][ProtocolPageNo])));
            if(cbLasEffect->ItemIndex < 0)
                cbLasEffect->ItemIndex = 0;
            cbLasDirectDropDown(this);
            cbLasDirect->ItemIndex = cbLasDirect->Items->IndexOf(AnsiString((char*)FormMain->ProtocolData.ProtocolLasDirect[ProtocolPageNo][FormMain->ASCIIBlockNo]).SubString(1, sizeof(FormMain->ProtocolData.ProtocolLasDirect[ProtocolPageNo][ProtocolPageNo])));
            if(cbLasDirect->ItemIndex < 0)
                cbLasDirect->ItemIndex = 0;
            cbSpeed->ItemIndex = cbSpeed->Items->IndexOf(AnsiString((char*)FormMain->ProtocolData.ProtocolSpeed[ProtocolPageNo][FormMain->ASCIIBlockNo]).SubString(1, sizeof(FormMain->ProtocolData.ProtocolSpeed[ProtocolPageNo][ProtocolPageNo])));
            cbDelay->ItemIndex = cbDelay->Items->IndexOf(AnsiString((char*)FormMain->ProtocolData.ProtocolDelay[ProtocolPageNo][FormMain->ASCIIBlockNo]).SubString(1, sizeof(FormMain->ProtocolData.ProtocolDelay[ProtocolPageNo][ProtocolPageNo])));
            cbStartXPos->ItemIndex = FormMain->ProtocolData.ProtocolStartXPos[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbStartYPos->ItemIndex = FormMain->ProtocolData.ProtocolStartYPos[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbEndXPos->ItemIndex = FormMain->ProtocolData.ProtocolEndXPos[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbEndYPos->ItemIndex = FormMain->ProtocolData.ProtocolEndYPos[ProtocolPageNo][FormMain->ASCIIBlockNo];
            if(FormMain->ASCIIBlockNo == 0)  
            {
                cbBGImage->ItemIndex = FormMain->ProtocolData.ProtocolBGImage[ProtocolPageNo][FormMain->ASCIIBlockNo];
                cbBGImage->Enabled = true;
                cbDelay->Enabled = true;
            }
            else
            {
                cbBGImage->Enabled = false;
                if(FormMain->bRealTimeIndex == 1)
                    cbDelay->Enabled = true;
                else
                    cbDelay->Enabled = false;
            }
            cbAssistEffect->ItemIndex = FormMain->ProtocolData.ProtocolColorType[ProtocolPageNo][FormMain->ASCIIBlockNo];
            cbTextColor->ItemIndex = FormMain->ProtocolData.ProtocolColor[ProtocolPageNo][FormMain->ASCIIBlockNo][0];
            cbTextBGColor->ItemIndex = FormMain->ProtocolData.ProtocolBGColor[ProtocolPageNo][FormMain->ASCIIBlockNo][0];
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::rbASCIINoSet()
{
        ProtocolPageNo = 11;
        FormMain->ASCIIPageNo = rbPageNo2->Checked;
        if(rbNo1->Checked)
            FormMain->ASCIIBlockNo = 0;
        else if(rbNo2->Checked)
            FormMain->ASCIIBlockNo = 1;
        else if(rbNo3->Checked)
            FormMain->ASCIIBlockNo = 2;
        FormMain->ProtocolData.ProtocolDispControl[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbDispControl->ItemIndex;
        FormMain->ProtocolData.ProtocolDispType[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbDispType->ItemIndex;
        FormMain->ProtocolData.ProtocolCodeKind[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbCodeKind->ItemIndex;
        FormMain->ProtocolData.ProtocolFontSize[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbFontSize->ItemIndex;
        FormMain->ProtocolData.ProtocolFontKind[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbFontKind->ItemIndex;
        ZeroMemory(&FormMain->ProtocolData.ProtocolEntEffect[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolEntEffect[0][0]));
        memcpy(&FormMain->ProtocolData.ProtocolEntEffect[ProtocolPageNo][FormMain->ASCIIBlockNo][0], AnsiString(cbEntEffect->Text).c_str(), AnsiString(cbEntEffect->Text).Length());
        ZeroMemory(&FormMain->ProtocolData.ProtocolLasDirect[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolLasDirect[0][0]));
        memcpy(&FormMain->ProtocolData.ProtocolLasDirect[ProtocolPageNo][FormMain->ASCIIBlockNo][0], AnsiString(cbEntDirect->Text).c_str(), AnsiString(cbEntDirect->Text).Length());
        ZeroMemory(&FormMain->ProtocolData.ProtocolLasEffect[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolLasEffect[0][0]));
        memcpy(&FormMain->ProtocolData.ProtocolLasEffect[ProtocolPageNo][FormMain->ASCIIBlockNo][0], AnsiString(cbLasEffect->Text).c_str(), AnsiString(cbLasEffect->Text).Length());
        ZeroMemory(&FormMain->ProtocolData.ProtocolLasDirect[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolLasDirect[0][0]));
        memcpy(&FormMain->ProtocolData.ProtocolLasDirect[ProtocolPageNo][FormMain->ASCIIBlockNo][0], AnsiString(cbLasDirect->Text).c_str(), AnsiString(cbLasDirect->Text).Length());
        FormMain->ProtocolData.ProtocolColorType[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbAssistEffect->ItemIndex;
        ZeroMemory(&FormMain->ProtocolData.ProtocolSpeed[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolSpeed[0][0]));
        memcpy(&FormMain->ProtocolData.ProtocolSpeed[ProtocolPageNo][FormMain->ASCIIBlockNo][0], AnsiString(cbSpeed->Text).c_str(), AnsiString(cbSpeed->Text).Length());
        ZeroMemory(&FormMain->ProtocolData.ProtocolDelay[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolDelay[0][0]));
        memcpy(&FormMain->ProtocolData.ProtocolDelay[ProtocolPageNo][FormMain->ASCIIBlockNo][0], AnsiString(cbDelay->Text).c_str(), AnsiString(cbDelay->Text).Length());
        FormMain->ProtocolData.ProtocolStartXPos[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbStartXPos->ItemIndex;
        FormMain->ProtocolData.ProtocolStartYPos[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbStartYPos->ItemIndex;
        FormMain->ProtocolData.ProtocolEndXPos[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbEndXPos->ItemIndex;
        FormMain->ProtocolData.ProtocolEndYPos[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbEndYPos->ItemIndex;
        FormMain->ProtocolData.ProtocolBGImage[ProtocolPageNo][FormMain->ASCIIBlockNo] = cbBGImage->ItemIndex;
        ZeroMemory(&FormMain->ProtocolData.ProtocolColor[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolColor[0][0]));
        FormMain->ProtocolData.ProtocolColor[ProtocolPageNo][FormMain->ASCIIBlockNo][0] = cbTextColor->ItemIndex;
        ZeroMemory(&FormMain->ProtocolData.ProtocolBGColor[ProtocolPageNo][FormMain->ASCIIBlockNo], sizeof(FormMain->ProtocolData.ProtocolBGColor[0][0]));
        FormMain->ProtocolData.ProtocolBGColor[ProtocolPageNo][FormMain->ASCIIBlockNo][0] = cbTextBGColor->ItemIndex;
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::BitBtnOkClick(TObject *Sender)
{
        //rbASCIINoSet();
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::btnSendClick(TObject *Sender)
{
        if(Sender == btnFactoryReset)
        {
            mASCIIText11->Text = "![" + FormMain->IntTo485Address(FormMain->Controler1) + "032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]";
            rbPageNo1->Checked = true;
            rbPageNo2->Checked = false;
            rbNo1->Checked = true;
            cbDispControl->ItemIndex = 1;
            cbDispType->ItemIndex = 1;
            cbCodeKind->ItemIndex = 0;
            cbFontSize->ItemIndex = 0;
            cbEntEffect->ItemIndex = 0;
            cbEntDirect->ItemIndex = 0;
            cbEntDirect->Text = FormMain->DirType[edNone];
            cbLasEffect->ItemIndex = 1;
            cbLasDirect->ItemIndex = 0;
            cbLasDirect->Text = FormMain->DirType[edNone];
            cbSpeed->ItemIndex = 2;
            cbDelay->ItemIndex = 1;
            cbStartXPos->ItemIndex = 0;
            cbEndXPos->ItemIndex = 0;
            cbStartYPos->ItemIndex = 0;
            cbEndYPos->ItemIndex = 0;
            cbBGImage->ItemIndex = 0;
            cbTextColor->ItemIndex = 3;
            cbTextBGColor->ItemIndex = 0;
            cbFontKind->ItemIndex = 0;
            return;
        }
        AnsiString asData = mASCIIText11->Text;
        btnSend->Enabled = false;
        bool bConnect = false;
        BYTE Addr = FormMain->Controler1;
        AnsiString asMessage = FormMain->LangFile.FormProtocolTab2;
        bConnect = FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false);
        if(bConnect)
        {
            AnsiString asSendData = asData;//"![0032" + asData + "!]";
            if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, asSendData.c_str(), asSendData.Length(), true))
            {
                if(FormMain->bDataFail)
                    FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
                else
                    FormMain->AddLog(asMessage + " [OK]" + FormMain->asLogMessage, clBlack);
            }
            else
                FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
            FormMain->ComClose(FormMain->ComKind);
        }
        btnSend->Enabled = true;
        ZeroMemory(&FormMain->ASCIIMessageData[10].MessageData, sizeof(FormMain->ASCIIMessageData[0].MessageData));
        if(asData.Length() > sizeof(FormMain->ASCIIMessageData[0].MessageData))
            memcpy(&FormMain->ASCIIMessageData[10].MessageData, asData.c_str(), sizeof(FormMain->ASCIIMessageData[0].MessageData));
        else
            memcpy(&FormMain->ASCIIMessageData[10].MessageData, asData.c_str(), asData.Length());
        asData = AnsiString((char *)FormMain->ASCIIMessageData[10].MessageData).SubString(1, 20);
        AnsiString ASCIIMessageFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "ASCIIMessageData.dat";
        TFileStream *ASCIIMessageFile = NULL;
        try{
            ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmCreate | fmShareCompat);
            ASCIIMessageFile->Write(&FormMain->ASCIIMessageData, sizeof(FormMain->ASCIIMessageData[0]) * 11);
        }catch(...){
        }
        if(ASCIIMessageFile)
        {
            delete ASCIIMessageFile;
            ASCIIMessageFile = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::btnReadClick(TObject *Sender)
{
        btnRead->Enabled = false;
        bool bConnect = false;
        BYTE Addr = 0;
        if(FormMain->bRS485)
            Addr = FormMain->Controler1;
        AnsiString asMessage = FormMain->LangFile.FormProtocolTab2;
        bConnect = FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false);
        if(bConnect)
        {
            AnsiString asSendData = "![" + FormMain->IntTo485Address(Addr) + "0330!]";
            if(FormMain->ModuleASCIIData(FormMain->ComKind, Addr, asSendData.c_str(), asSendData.Length(), true))
            {
                if(FormMain->bDataFail)
                    FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
                else
                    FormMain->AddLog(asMessage + " [OK]" + FormMain->asLogMessage, clBlack);
            }
            else
                FormMain->AddLog(asMessage + " [FAIL]" + FormMain->asLogMessage, clRed);
            FormMain->ComClose(FormMain->ComKind);
        }
        btnRead->Enabled = true;
        AnsiString asData = mASCIIText11->Text;
        ZeroMemory(&FormMain->ASCIIMessageData[10].MessageData, sizeof(FormMain->ASCIIMessageData[0].MessageData));
        if(asData.Length() > sizeof(FormMain->ASCIIMessageData[0].MessageData))
            memcpy(&FormMain->ASCIIMessageData[10].MessageData, asData.c_str(), sizeof(FormMain->ASCIIMessageData[0].MessageData));
        else
            memcpy(&FormMain->ASCIIMessageData[10].MessageData, asData.c_str(), asData.Length());
        asData = AnsiString((char *)FormMain->ASCIIMessageData[10].MessageData).SubString(1, 20);
        AnsiString ASCIIMessageFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "ASCIIMessageData.dat";
        TFileStream *ASCIIMessageFile = NULL;
        try{
            ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmCreate | fmShareCompat);
            ASCIIMessageFile->Write(&FormMain->ASCIIMessageData, sizeof(FormMain->ASCIIMessageData[0]) * 11);
        }catch(...){
        }
        if(ASCIIMessageFile)
        {
            delete ASCIIMessageFile;
            ASCIIMessageFile = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::btnSetClick(TObject *Sender)
{
        AnsiString asMessage = "![" + FormMain->IntTo485Address(FormMain->Controler1) + "032/P";
        AnsiString astemp = "";
        AnsiString astemp2 = "";
        int temp = 0;
        char Buf[4];
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", rbPageNo2->Checked);
        asMessage = asMessage + AnsiString(Buf);
        if(rbNo1->Checked)
            FormMain->ASCIIBlockNo = 0;
        else if(rbNo2->Checked)
            FormMain->ASCIIBlockNo = 1;
        else if(rbNo3->Checked)
            FormMain->ASCIIBlockNo = 2;
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", FormMain->ASCIIBlockNo);
        asMessage = asMessage + AnsiString(Buf) + "/D";
        if(cbDispControl->ItemIndex == 0)
            temp = 0;
        else if(cbDispControl->ItemIndex == 1)
            temp = 99;
        else
            temp = cbDispControl->ItemIndex - 1;
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", temp);
        asMessage = asMessage + AnsiString(Buf);
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", cbDispType->ItemIndex);
        asMessage = asMessage + AnsiString(Buf) + "/F";
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", cbCodeKind->ItemIndex);
        asMessage = asMessage + AnsiString(Buf);
        //if(cbFontSize->ItemIndex == 0)
        //    temp = 1;
        //else
            temp = cbFontSize->ItemIndex + 3;
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", temp);
        asMessage = asMessage + AnsiString(Buf) + "/E";
        astemp = cbEntEffect->Text;
        astemp2 = cbEntDirect->Text;
        temp = FormMain->InputSchEffect((TGEffectType)FormMain->FindItemIndex(astemp, 2), (TGEffectDirection)FormMain->FindItemIndex(astemp2, 3));
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", temp);
        asMessage = asMessage + AnsiString(Buf);
        astemp = cbLasEffect->Text;
        astemp2 = cbLasDirect->Text;
        temp = FormMain->InputSchEffect((TGEffectType)FormMain->FindItemIndex(astemp, 2), (TGEffectDirection)FormMain->FindItemIndex(astemp2, 3));
        ZeroMemory(Buf, 3);
        wsprintf(Buf, "%02d", temp);
        asMessage = asMessage + AnsiString(Buf) + "/S";
        astemp = cbSpeed->Text;
        if(!astemp.SubString(astemp.Pos("(") + 1, AnsiString(FormMain->LangFile.Message01).Length()).AnsiCompareIC(FormMain->LangFile.Message01))
            temp = 5;
        else if(!astemp.SubString(astemp.Pos("(") + 1, AnsiString(FormMain->LangFile.Message02).Length()).AnsiCompareIC(FormMain->LangFile.Message02))
            temp = 99;
        else
            temp = StrToIntDef(astemp.TrimRight(), 20);
        ZeroMemory(Buf, 3);
        wsprintf(Buf, "%02d", temp);
        asMessage = asMessage + AnsiString(Buf);
        astemp = cbDelay->Text;
        if(astemp.Pos("("))
            temp = FormMain->SetDelayTime(StrToIntDef(astemp.SubString(1, astemp.Pos("(") - 1).Trim(), 2));
        else
            temp = FormMain->SetDelayTime(StrToIntDef(astemp, 2)) / 2;
        ZeroMemory(Buf, 3);
        wsprintf(Buf, "%02d", temp);
        asMessage = asMessage + AnsiString(Buf) + "/X";
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", cbStartXPos->ItemIndex);
        asMessage = asMessage + AnsiString(Buf);
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", cbEndXPos->ItemIndex);
        asMessage = asMessage + AnsiString(Buf) + "/Y";
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", cbStartYPos->ItemIndex);
        asMessage = asMessage + AnsiString(Buf);
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%02d", cbEndYPos->ItemIndex);
        asMessage = asMessage + AnsiString(Buf) + "/B";
        if(cbBGImage->ItemIndex >= 11)
            temp = cbBGImage->ItemIndex + 190;
        else
            temp = cbBGImage->ItemIndex;
        ZeroMemory(Buf, sizeof(Buf));
        wsprintf(Buf, "%03d", temp);
        asMessage = asMessage + AnsiString(Buf) + "/C";
        asMessage = asMessage + IntToStr(cbTextColor->ItemIndex) + "/G";
        asMessage = asMessage + IntToStr(cbTextBGColor->ItemIndex) + "/T";
        asMessage = asMessage + IntToStr(cbFontKind->ItemIndex) + "!]";
        mASCIIText11->Text = asMessage;
        rbASCIINoSet();
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::cbFontSizeClick(TObject *Sender)
{
        // 긴급문구 12x12 폰트는 배경색을 지정 못하게 막는다
        /*if(cbFontSize->ItemIndex == 0)
        {
            eBGColor->Enabled = false;
            Label18->Enabled = false;
        }
        else
        {
            eBGColor->Enabled = true;
            Label18->Enabled = true;
        }*/
}
//---------------------------------------------------------------------------  

void __fastcall TFormASCIISet::cbEntEffectClick(TObject *Sender)
{
        TGEffectType ED = (TGEffectType)FormMain->FindItemIndex(cbEntEffect->Text, 2);
        cbEntDirect->Clear();
        cbEntDirect->ItemIndex = -1;
        switch(ED)
        {
            case etNormal:
                cbEntDirect->Items->Add(FormMain->DirType[edNone]);
            break;
            case etShift:
                cbEntDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etWipe:
            case et3DShift:
                cbEntDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etBlind:
                cbEntDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etCurtain:
                cbEntDirect->Items->Add(FormMain->DirType[edHoriSide]);
            break;
            case etScaleIn:
                cbEntDirect->Items->Add(FormMain->DirType[edLeftUp]);
            break;
            case etScaleOut:
                cbEntDirect->Items->Add(FormMain->DirType[edRightDown]);
            break;
            case etRotate:
                cbEntDirect->Items->Add(FormMain->DirType[edReverseClock]);
            break;
            case etBlink:
            case etBlinkReverse:
                cbEntDirect->Items->Add(FormMain->DirType[edRed]);
            break;
            case etLineTest:
                cbEntDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etRandom:
                cbEntDirect->Items->Add(FormMain->DirType[edRandom]);
            break;
        }
        cbEntDirect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::cbEntEffectDropDown(TObject *Sender)
{
        if(cbEntEffect->Items->IndexOf(cbEntEffect->Text) > -1)
            cbEntEffect->ItemIndex = cbEntEffect->Items->IndexOf(cbEntEffect->Text);
        else
            cbEntEffect->ItemIndex = 0;        
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::cbEntDirectDropDown(TObject *Sender)
{
        AnsiString asED = cbEntDirect->Text;
        cbEntDirect->Clear();
        if(cbEntEffect->Text == FormMain->EffectType[etNormal])
        {
            for(int i = edNone; i < 5; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etShift])
        {
            for(int i = edLeft; i < edLeft + 6; i++)
            {
                if((TGEffectType)i == edLeftUp)
                    continue;
                if((TGEffectType)i == edUpDown)
                    continue;
                cbEntDirect->Items->Add(FormMain->DirType[i]);
            }
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etWipe] ||
            cbEntEffect->Text == FormMain->EffectType[etBlind])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etCurtain])
        {
            for(int i = edHoriSide; i < edHoriSide + 4; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etScaleIn] ||
            cbEntEffect->Text == FormMain->EffectType[etScaleOut])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
            if(cbEntEffect->Text == FormMain->EffectType[etScaleIn])
                cbEntDirect->Items->Add(FormMain->DirType[edLeftUp]);
            else
                cbEntDirect->Items->Add(FormMain->DirType[edRightDown]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etRotate])
        {
            for(int i = edReverseClock; i < edReverseClock + 4; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etBlink])
        {
            for(int i = edRed; i < edRed + 5; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etBlinkReverse])
        {
            for(int i = edRed; i < edRed + 6; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etLineTest])
        {
            for(int i = edLeft; i < edLeft + 10; i++)
            {
                if((TGEffectType)i == edLeftUp || (TGEffectType)i == edUpDown || (TGEffectType)i == edLeftAdd)
                    continue;
                if((TGEffectType)i == edLeft || (TGEffectType)i == edDown || (TGEffectType)i == edRightDown)
                    cbEntDirect->Items->Add(FormMain->DirType[i]);
            }
        }
        else if(cbEntEffect->Text == FormMain->EffectType[etRandom])
        {
            for(int i = edOrder + 1; i < edOrder + 2; i++)
                cbEntDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbEntEffect->Text == FormMain->EffectType[et3DShift])
            cbEntDirect->Items->Add(FormMain->DirType[edLeft]);
        if(cbEntDirect->Items->IndexOf(asED) > -1)
            cbEntDirect->ItemIndex = cbEntDirect->Items->IndexOf(asED);
        else
            cbEntDirect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::cbLasEffectClick(TObject *Sender)
{
        TGEffectType ED = (TGEffectType)FormMain->FindItemIndex(cbLasEffect->Text, 2);
        cbLasDirect->Clear();
        cbLasDirect->ItemIndex = -1;
        switch(ED)
        {
            case etNormal:
                cbLasDirect->Items->Add(FormMain->DirType[edNone]);
            break;
            case etShift:
            case et3DShift:
                cbLasDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etWipe:
                cbLasDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etBlind:
                cbLasDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etCurtain:
                cbLasDirect->Items->Add(FormMain->DirType[edHoriSide]);
            break;
            case etScaleIn:
                cbLasDirect->Items->Add(FormMain->DirType[edLeftUp]);
            break;
            case etScaleOut:
                cbLasDirect->Items->Add(FormMain->DirType[edRightDown]);
            break;
            case etRotate:
                cbLasDirect->Items->Add(FormMain->DirType[edReverseClock]);
            break;
            case etBlink:
            case etBlinkReverse:
                cbLasDirect->Items->Add(FormMain->DirType[edRed]);
            break;
            case etLineTest:
                cbLasDirect->Items->Add(FormMain->DirType[edLeft]);
            break;
            case etRandom:
                cbLasDirect->Items->Add(FormMain->DirType[edRandom]);
            break;
            default:
                cbLasDirect->Items->Add(FormMain->LangFile.NoEffect);
            break;
        }
        cbLasDirect->ItemIndex = 0;        
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::cbLasEffectDropDown(TObject *Sender)
{
        if(cbLasEffect->Items->IndexOf(cbLasEffect->Text) > -1)
            cbLasEffect->ItemIndex = cbLasEffect->Items->IndexOf(cbLasEffect->Text);
        else
            cbLasEffect->ItemIndex = 0;       
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::cbLasDirectDropDown(TObject *Sender)
{
        AnsiString asED = cbLasDirect->Text;
        cbLasDirect->Clear();
        if(cbLasEffect->Text == FormMain->EffectType[etNormal])
        {
            for(int i = edNone; i < 5; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etShift])
        {
            for(int i = edLeft; i < edLeft + 6; i++)
            {
                if((TGEffectType)i == edLeftUp)
                    continue;
                if((TGEffectType)i == edUpDown)
                    continue;
                cbLasDirect->Items->Add(FormMain->DirType[i]);
            }
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etWipe] ||
            cbLasEffect->Text == FormMain->EffectType[etBlind])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etCurtain])
        {
            for(int i = edHoriSide; i < edHoriSide + 4; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etScaleIn] ||
            cbLasEffect->Text == FormMain->EffectType[etScaleOut])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
            if(cbLasEffect->Text == FormMain->EffectType[etScaleIn])
                cbLasDirect->Items->Add(FormMain->DirType[edLeftUp]);
            else
                cbLasDirect->Items->Add(FormMain->DirType[edRightDown]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etRotate])
        {
            for(int i = edReverseClock; i < edReverseClock + 4; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etBlink])
        {
            for(int i = edRed; i < edRed + 5; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etBlinkReverse])
        {
            for(int i = edRed; i < edRed + 6; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etLineTest])
        {
            for(int i = edLeft; i < edLeft + 10; i++)
            {
                if((TGEffectType)i == edLeftUp || (TGEffectType)i == edUpDown || (TGEffectType)i == edLeftAdd)
                    continue;
                if((TGEffectType)i == edLeft || (TGEffectType)i == edDown || (TGEffectType)i == edRightDown)
                    cbLasDirect->Items->Add(FormMain->DirType[i]);
            }
        }
        else if(cbLasEffect->Text == FormMain->EffectType[etRandom])
        {
            for(int i = edOrder + 1; i < edOrder + 2; i++)
                cbLasDirect->Items->Add(FormMain->DirType[i]);
        }
        else if(cbLasEffect->Text == FormMain->EffectType[et3DShift])
            cbLasDirect->Items->Add(FormMain->DirType[edLeft]);
        else
            cbLasDirect->Items->Add(FormMain->LangFile.NoEffect);
        if(cbLasDirect->Items->IndexOf(asED) > -1)
            cbLasDirect->ItemIndex = cbLasDirect->Items->IndexOf(asED);
        else
            cbLasDirect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormASCIISet::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormASCIISet::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormASCIISet::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

