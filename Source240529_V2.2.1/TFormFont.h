//---------------------------------------------------------------------------

#ifndef TFormFontH
#define TFormFontH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "te_controls.hpp"
#include "TntStdCtrls.hpp"
#include "TntButtons.hpp"
#include <Buttons.hpp>
#include "TntExtCtrls.hpp"
#include <ExtCtrls.hpp>
#include "TFormFontName.h"
//---------------------------------------------------------------------------
class TFormFont : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnDivide;
        TTntPanel *gbFont1;
        TTntPanel *pnFont1;
        TTntLabel *laFontLabel1;
        TTntComboBox *cbFontSize1;
        TTntComboBox *cbFontKind11;
        TTntComboBox *cbFontKind12;
        TTntComboBox *cbFontKind13;
        TEdit *eFont13;
        TEdit *eFont12;
        TEdit *eFont11;
        TTntLabel *laFontLabel2;
        TTntPanel *pnsbPathFont13;
        TTntSpeedButton *sbPathFont13;
        TTntPanel *pnsbPathFont12;
        TTntSpeedButton *sbPathFont12;
        TTntPanel *pnsbPathFont11;
        TTntSpeedButton *sbPathFont11;
        TTntLabel *laFontSize13;
        TTntLabel *laFontSize12;
        TTntLabel *laFontSize11;
        TTntPanel *gbFont2;
        TTntPanel *pnFont2;
        TTntPanel *pnsbPathFont23;
        TTntSpeedButton *sbPathFont23;
        TTntPanel *pnsbPathFont22;
        TTntSpeedButton *sbPathFont22;
        TTntPanel *pnsbPathFont21;
        TTntSpeedButton *sbPathFont21;
        TTntLabel *laFontSize23;
        TTntLabel *laFontSize22;
        TTntLabel *laFontSize21;
        TTntLabel *laFontLabel6;
        TTntLabel *laFontLabel3;
        TEdit *eFont23;
        TEdit *eFont22;
        TEdit *eFont21;
        TTntComboBox *cbFontSize2;
        TTntComboBox *cbFontKind23;
        TTntComboBox *cbFontKind22;
        TTntComboBox *cbFontKind21;
        TTntCheckBox *cbFont2;
        TTntPanel *gbFont3;
        TTntPanel *pnFont3;
        TTntPanel *gbFont4;
        TTntPanel *pnFont4;
        TTntCheckBox *cbFont3;
        TTntCheckBox *cbFont4;
        TTntPanel *pnsbPathFont33;
        TTntSpeedButton *sbPathFont33;
        TTntPanel *pnsbPathFont32;
        TTntSpeedButton *sbPathFont32;
        TTntPanel *pnsbPathFont31;
        TTntSpeedButton *sbPathFont31;
        TTntLabel *laFontSize33;
        TTntLabel *laFontSize32;
        TTntLabel *laFontSize31;
        TTntLabel *laFontLabel8;
        TTntLabel *laFontLabel7;
        TEdit *eFont33;
        TEdit *eFont32;
        TEdit *eFont31;
        TTntComboBox *cbFontSize3;
        TTntComboBox *cbFontKind33;
        TTntComboBox *cbFontKind32;
        TTntComboBox *cbFontKind31;
        TTntPanel *pnsbPathFont43;
        TTntSpeedButton *sbPathFont43;
        TTntPanel *pnsbPathFont42;
        TTntSpeedButton *sbPathFont42;
        TTntPanel *pnsbPathFont41;
        TTntSpeedButton *sbPathFont41;
        TTntLabel *laFontSize43;
        TTntLabel *laFontSize42;
        TTntLabel *laFontSize41;
        TTntLabel *laFontLabel9;
        TTntLabel *laFontLabel10;
        TEdit *eFont43;
        TEdit *eFont42;
        TEdit *eFont41;
        TTntComboBox *cbFontSize4;
        TTntComboBox *cbFontKind43;
        TTntComboBox *cbFontKind42;
        TTntComboBox *cbFontKind41;
        TTntPanel *pnBtnSend;
        TTntSpeedButton *BtnSend;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntLabel *laTotFontSize;
        TTntCheckBox *cbFont1;
        TTntPanel *pnBtnRead;
        TTntSpeedButton *BtnRead;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall sbPathFontClick(TObject *Sender);
        void __fastcall BtnSendClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall cbFontKind12Click(TObject *Sender);
        void __fastcall cbFontKind13Ciick(TObject *Sender);
        void __fastcall cbFontKind22Ciick(TObject *Sender);
        void __fastcall cbFontKind23Ciick(TObject *Sender);
        void __fastcall cbFont2Click(TObject *Sender);
        void __fastcall cbFontKind32Ciick(TObject *Sender);
        void __fastcall cbFontKind33Ciick(TObject *Sender);
        void __fastcall cbFontKind42Ciick(TObject *Sender);
        void __fastcall cbFontKind43Ciick(TObject *Sender);
        void __fastcall cbFont3Click(TObject *Sender);
        void __fastcall cbFont4Click(TObject *Sender);
        void __fastcall cbFontKind21Click(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall cbFont1Click(TObject *Sender);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall BtnReadClick(TObject *Sender);
private:
        TFormFontName *FFontName;     // 폰트 이름 읽어오기 폼
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
        bool __fastcall SendFontData(BYTE ComKind, int Addr);
        int __fastcall FindCodeIndex(AnsiString CodeName);	// User declarations
        WideString __fastcall FindCodeIndexString(int iCodeIndex);
        int __fastcall GetFontValue(int iIndex);
public:		// User declarations
        __fastcall TFormFont(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormFont *FormFont;
//---------------------------------------------------------------------------
#endif
