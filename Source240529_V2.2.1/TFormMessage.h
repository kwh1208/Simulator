//---------------------------------------------------------------------------

#ifndef TFormMessageH
#define TFormMessageH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Buttons.hpp>
#include "te_controls.hpp"
#include "TntStdCtrls.hpp"
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormMessage : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntLabel *laMessage;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnbtnYes;
        TTntSpeedButton *btnYes;
        TTntPanel *pnbtnNo;
        TTntSpeedButton *btnNo;
        TTntPanel *pnbtnCancel;
        TTntSpeedButton *btnCancel;
        TTntPanel *pnDivide;
        void __fastcall btnYesClick(TObject *Sender);
        void __fastcall btnNoClick(TObject *Sender);
        void __fastcall btnCancelClick(TObject *Sender);
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
        __fastcall TFormMessage(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormMessage *FormMessage;
//---------------------------------------------------------------------------
#endif
