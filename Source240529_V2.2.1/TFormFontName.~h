//---------------------------------------------------------------------------

#ifndef TFormFontNameH
#define TFormFontNameH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
#include "TntStdCtrls.hpp"
#include <Buttons.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormFontName : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntLabel *laTotFontSize;
        TTntPanel *gbFont1;
        TTntPanel *pnFont1;
        TTntLabel *laFontLabel11;
        TTntLabel *laFontLabel12;
        TTntLabel *laFontLabel13;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnDivide;
        TTntPanel *gbFont2;
        TTntPanel *pnFont2;
        TTntLabel *laFontLabel21;
        TTntLabel *laFontLabel22;
        TTntLabel *laFontLabel23;
        TTntPanel *pnBtnSend;
        TTntSpeedButton *BtnSend;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntPanel *laFont1;
        TTntPanel *laFont2;
        TEdit *eFont11;
        TEdit *eFont12;
        TEdit *eFont13;
        TEdit *eFont21;
        TEdit *eFont22;
        TEdit *eFont23;
        TTntPanel *gbFontReadWrite;
        TTntPanel *pnFontReadWrite;
        TTntRadioButton *rgRead;
        TTntRadioButton *rgWrite;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall BtnSendClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall rgReadWriteClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
private:	// User declarations
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
public:		// User declarations
        __fastcall TFormFontName(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormFontName *FormFontName;
//---------------------------------------------------------------------------
#endif
