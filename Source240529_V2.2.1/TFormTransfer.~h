//---------------------------------------------------------------------------

#ifndef TFormTransferH
#define TFormTransferH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ComCtrls.hpp>
#include "Protocol.h"
#include <Buttons.hpp>
#include "te_controls.hpp"
#include "SUIProgressBar.hpp"
#include <ExtCtrls.hpp>
#include <Graphics.hpp>
#include "TntStdCtrls.hpp"
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
//---------------------------------------------------------------------------
class TFormTransfer : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TLabel *Label2;
        TTntLabel *Label6;
        TTntLabel *Label1;
        TTntLabel *LabelTotPercent;
        TTntLabel *LabelTotalPacket;
        TTntLabel *LabelSign;
        TTntLabel *LabelName;
        TTntLabel *LabelCount;
        TsuiProgressBar *ProgressBar;
        TsuiProgressBar *ProgressBar1;
        TTntPanel *pnBCancel;
        TTntSpeedButton *BCancel;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnDivide;
        void __fastcall FormClose(TObject *Sender, TCloseAction &Action);
        void __fastcall FormShow(TObject *Sender);
        void __fastcall BCancelClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
private:	// User declarations
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
public:		// User declarations
        __fastcall TFormTransfer(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormTransfer *FormTransfer;
//---------------------------------------------------------------------------
#endif
