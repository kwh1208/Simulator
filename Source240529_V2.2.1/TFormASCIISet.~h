//---------------------------------------------------------------------------

#ifndef TFormASCIISetH
#define TFormASCIISetH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "te_controls.hpp"
#include "TntButtons.hpp"
#include "TntStdCtrls.hpp"
#include <Buttons.hpp>
#include "TntExtCtrls.hpp"
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormASCIISet : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntLabel *Label21;
        TTntLabel *Label17;
        TTntLabel *Label15;
        TTntLabel *Label13;
        TTntLabel *Label24;
        TTntLabel *Label23;
        TTntLabel *Label16;
        TTntLabel *Label14;
        TTntLabel *Label12;
        TTntLabel *Label11;
        TTntLabel *Label10;
        TTntLabel *Label9;
        TTntLabel *Label8;
        TTntLabel *Label7;
        TTntLabel *Label6;
        TTntLabel *Label5;
        TTntLabel *Label4;
        TTntLabel *Label3;
        TTntLabel *Label2;
        TTntLabel *Label1;
        TTntLabel *Label18;
        TTntComboBox *cbFontKind;
        TTntComboBox *cbBGImage;
        TTntComboBox *cbEndYPos;
        TTntComboBox *cbEndXPos;
        TTntComboBox *cbStartYPos;
        TTntComboBox *cbStartXPos;
        TTntComboBox *cbDelay;
        TTntComboBox *cbSpeed;
        TTntComboBox *cbAssistEffect;
        TTntComboBox *cbLasDirect;
        TTntComboBox *cbLasEffect;
        TTntComboBox *cbEntEffect;
        TTntComboBox *cbEntDirect;
        TTntComboBox *cbFontSize;
        TTntComboBox *cbCodeKind;
        TTntComboBox *cbDispType;
        TTntComboBox *cbDispControl;
        TTntMemo *mASCIIText11;
        TTntComboBox *cbTextBGColor;
        TTntComboBox *cbTextColor;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnbtnSet;
        TTntSpeedButton *btnSet;
        TTntPanel *pnbtnSend;
        TTntSpeedButton *btnSend;
        TTntPanel *pnbtnRead;
        TTntSpeedButton *btnRead;
        TTntPanel *pnbtnFactoryReset;
        TTntSpeedButton *btnFactoryReset;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntPanel *pnDivide;
        TTntPanel *rgPageNo;
        TTntPanel *pnPageNo;
        TTntRadioButton *rbPageNo1;
        TTntRadioButton *rbPageNo2;
        TTntPanel *rbTextNo;
        TTntPanel *pnTextNo;
        TTntRadioButton *rbNo1;
        TTntRadioButton *rbNo2;
        TTntRadioButton *rbNo3;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall rbASCIINoRoadClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall btnSendClick(TObject *Sender);
        void __fastcall btnReadClick(TObject *Sender);
        void __fastcall btnSetClick(TObject *Sender);
        void __fastcall cbFontSizeClick(TObject *Sender);
        void __fastcall cbEntEffectClick(TObject *Sender);
        void __fastcall cbEntEffectDropDown(TObject *Sender);
        void __fastcall cbEntDirectDropDown(TObject *Sender);
        void __fastcall cbLasEffectClick(TObject *Sender);
        void __fastcall cbLasEffectDropDown(TObject *Sender);
        void __fastcall cbLasDirectDropDown(TObject *Sender);
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
        int ProtocolPageNo; // ASCII문구 저장 페이지
        int ProtocolBlockNo; // ASCII문구 저장 섹션
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
        void __fastcall rbASCIINoSet();
public:		// User declarations
        __fastcall TFormASCIISet(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormASCIISet *FormASCIISet;
//---------------------------------------------------------------------------
#endif
