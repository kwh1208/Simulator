//---------------------------------------------------------------------------

#ifndef TFormSettingH
#define TFormSettingH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "TntButtons.hpp"
#include "TntComCtrls.hpp"
#include "TntStdCtrls.hpp"
#include <Buttons.hpp>
#include <ComCtrls.hpp>
#include "TFormComPort.h"
#include "TFormFont.h"
#include "TFormLEDSet.h"
#include "TFormFirmware.h"
#include "TntExtCtrls.hpp"
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormSetting : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntPanel *pnBtnETC;
        TTntSpeedButton *BtnETC;
        TTntPanel *pnBtnComPort;
        TTntSpeedButton *BtnComPort;
        TTntPanel *pnDivide;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnBtnOk;
        TTntSpeedButton *BtnOk;
        TTntPanel *pnBtnLog;
        TTntSpeedButton *BtnLog;
        TTntPanel *pnBtnLEDSetting;
        TTntSpeedButton *BtnLEDSetting;
        TTntPanel *pnBtnFontSend;
        TTntSpeedButton *BtnFontSend;
        TTntPanel *pnBtnDIBDDownload;
        TTntSpeedButton *BtnDIBDDownload;
        TTntPanel *pnBtnApply;
        TTntSpeedButton *BtnApply;
        TTntPanel *gbSignSize;
        TTntPanel *gbProtocol;
        TTntPanel *laProtocol;
        TTntPanel *pnSignSize;
        TTntPanel *pnProtocol;
        TTntRadioButton *rgASCII;
        TTntRadioButton *rgHex;
        TTntPanel *pnBtnSignSize;
        TTntComboBox *cbProgramType;
        TTntComboBox *cbDirection;
        TTntCheckBox *cbDirectCheck;
        TTntUpDown *UpDownWidth;
        TTntUpDown *UpDownHeight;
        TTntLabel *TntLabel6;
        TTntLabel *TntLabel5;
        TTntLabel *TntLabel3;
        TTntLabel *TntLabel2;
        TTntLabel *TntLabel11;
        TTntLabel *TntLabel10;
        TTntLabel *TntLabel1;
        TTntEdit *TntEdit2;
        TTntEdit *TntEdit1;
        TTntSpeedButton *BtnSignSize;
        TTntPanel *laSignSize;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall BtnApplyClick(TObject *Sender);
        void __fastcall BtnOkClick(TObject *Sender);
        void __fastcall BtnFontSendClick(TObject *Sender);
        void __fastcall BtnLEDSettingClick(TObject *Sender);
        void __fastcall BtnDIBDDownloadClick(TObject *Sender);
        void __fastcall BtnComPortClick(TObject *Sender);
        void __fastcall BtnLogClick(TObject *Sender);
        void __fastcall BtnSignSizeClick(TObject *Sender);
        void __fastcall cbDirectCheckClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall BtnETCClick(TObject *Sender);
        void __fastcall TntLabel10Click(TObject *Sender);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
private:	// User declarations
        TFormFont *FFont;     // ÆùÆ® Àü¼Û Æû
        TFormLEDSet *FLEDSet; // LED ¸ðµâ Á¾·ù ¼³Á¤ Æû
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
        void __fastcall ButtonEnabled(bool bEnabled);
public:		// User declarations
        TFormComPort *FComPort; // Åë½Å¼³Á¤ Æû
        TFormFirmware *FFirmware; // Æß¿þ¾î Àü¼ÛÃ¢ Æû
        __fastcall TFormSetting(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormSetting *FormSetting;
//---------------------------------------------------------------------------
#endif
