//---------------------------------------------------------------------------

#ifndef TFormETCH
#define TFormETCH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "te_controls.hpp"
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
#include "TntStdCtrls.hpp"
#include <Buttons.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormETC : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TLabel *Label25;
        TTntPanel *pnBitBtnPowerLEDOn;
        TTntSpeedButton *BitBtnPowerLEDOn;
        TTntPanel *pnBitBtnPowerLEDOff;
        TTntSpeedButton *BitBtnPowerLEDOff;
        TTntPanel *pnBtnTimeRead;
        TTntSpeedButton *BtnTimeRead;
        TTntPanel *pnBtnTimeSet;
        TTntSpeedButton *BtnTimeSet;
        TTntPanel *pnBtnFReset;
        TTntSpeedButton *BtnFReset;
        TTntPanel *pnBtnMsgErase;
        TTntSpeedButton *BtnMsgErase;
        TTntPanel *pnBtnOk;
        TTntSpeedButton *BtnOk;
        TTntPanel *pnDivide;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *gbMemInput;
        TTntPanel *pnMemInput;
        TTntPanel *pnBtnMemInput;
        TTntSpeedButton *BtnMemInput;
        TTntComboBox *cbMsgInput;
        TTntPanel *laMemInput;
        TTntPanel *gbMsgErase;
        TTntPanel *pnMsgErase;
        TTntPanel *gbBGImage;
        TTntPanel *pnBGImage;
        TTntPanel *gbSync;
        TTntPanel *pnSync;
        TTntPanel *gbBright;
        TTntPanel *pnBright;
        TTntPanel *laMsgErase;
        TTntComboBox *cbMsgMemClear;
        TTntPanel *pnBtnMsgMemClear;
        TTntSpeedButton *BtnMsgMemClear;
        TTntComboBox *cbBGISelect;
        TTntPanel *pnBtnBGImage;
        TTntSpeedButton *BtnBGImage;
        TTntPanel *laBGImage;
        TTntPanel *laBright;
        TTntComboBox *cbBright;
        TTntPanel *pnBtnBright;
        TTntSpeedButton *BtnBright;
        TTntComboBox *cbSoundSec1;
        TTeLabel *SLabel1;
        TTeLabel *SLabel2;
        TTntComboBox *cbSoundSec2;
        TTntPanel *pnBtnSendSync;
        TTntSpeedButton *BtnSendSync;
        TTntPanel *laSync;
        TTntLabel *TntLabel8;
        TTntComboBox *cbWaitTime;
        TTntLabel *TntLabel9;
        void __fastcall BtnMsgMemClearClick(TObject *Sender);
        void __fastcall BtnMsgEraseClick(TObject *Sender);
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall BtnSendSyncClick(TObject *Sender);
        void __fastcall BtnFResetClick(TObject *Sender);
        void __fastcall BtnBrightClick(TObject *Sender);
        void __fastcall BtnMemInputClick(TObject *Sender);
        void __fastcall BitBtnPowerLEDClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall BtnOkClick(TObject *Sender);
        void __fastcall BtnTimeSetClick(TObject *Sender);
        void __fastcall BtnTimeReadClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall cbWaitTimeClick(TObject *Sender);
private:	// User declarations
        int OldXPos;
        int OldYPos;
        void __fastcall ButtonEnabled(bool bEnabled);
public:		// User declarations
        __fastcall TFormETC(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormETC *FormETC;
//---------------------------------------------------------------------------
#endif
