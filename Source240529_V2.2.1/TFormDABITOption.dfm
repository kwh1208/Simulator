object FormDABITOption: TFormDABITOption
  Left = 924
  Top = 244
  BorderIcons = [biMinimize, biMaximize]
  BorderStyle = bsNone
  Caption = 'FormDABITOption'
  ClientHeight = 373
  ClientWidth = 325
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object pnMainDivide: TTntPanel
    Left = 0
    Top = 0
    Width = 326
    Height = 373
    Align = alCustom
    Alignment = taLeftJustify
    BevelOuter = bvNone
    TabOrder = 0
    object pnMain: TTntPanel
      Left = 4
      Top = 4
      Width = 317
      Height = 365
      BevelOuter = bvNone
      Color = clBlack
      TabOrder = 0
      object laLabel2: TTntLabel
        Left = 12
        Top = 65
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '2. Sub Command'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel3: TTntLabel
        Left = 12
        Top = 93
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '3. Debugging Method'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel6: TTntLabel
        Left = 12
        Top = 171
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '6. J2(RS232) Baudrate'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel1: TTntLabel
        Left = 12
        Top = 39
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '1. Command'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel4: TTntLabel
        Left = 12
        Top = 119
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '4. BH1(TTL/RS485) Function'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel5: TTntLabel
        Left = 12
        Top = 145
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '5. J4(4pin) Function'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel9: TTntLabel
        Left = 12
        Top = 246
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '9. Address'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel10: TTntLabel
        Left = 172
        Top = 246
        Width = 128
        Height = 19
        AutoSize = False
        Caption = '00'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel7: TTntLabel
        Left = 12
        Top = 197
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '7. J3(TTL) Baudrate'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object laLabel8: TTntLabel
        Left = 12
        Top = 223
        Width = 160
        Height = 19
        AutoSize = False
        Caption = '8. BH1(TTL/RS485) Baudrate'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object pnDataSend: TTntPanel
        Left = 211
        Top = 277
        Width = 88
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 0
        object DataSend: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 88
          Height = 30
          Caption = #51204#49569
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = DataSendClick
          Caption_UTF7 = '+yATBoQ'
        end
      end
      object pnBitBtnOk: TTntPanel
        Left = 211
        Top = 320
        Width = 88
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 1
        object BitBtnOk: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 88
          Height = 30
          Caption = #54869#51064'(&S)'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BitBtnOkClick
          Caption_UTF7 = '+1lXHeA(&S)'
        end
      end
      object pnDivide: TTntPanel
        Left = 0
        Top = 25
        Width = 317
        Height = 2
        Align = alTop
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clBlack
        TabOrder = 2
      end
      object cbDubug: TTntComboBox
        Left = 172
        Top = 92
        Width = 128
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 3
        OnClick = cbDubugClick
        Items.WideStrings = (
          'Disable'
          'Enable1'
          'Enable2'
          'Enable3'
          'Enable4'
          'Enable5'
          'Enable6'
          'Enable7'
          'Enable8')
      end
      object cbBoardRate232: TTntComboBox
        Left = 172
        Top = 170
        Width = 128
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 4
        Items.WideStrings = (
          '9600'
          '19200'
          '38400'
          '57600'
          '115200'
          '230400'
          '460800'
          '921600')
      end
      object cbBH1: TTntComboBox
        Left = 172
        Top = 118
        Width = 128
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 5
        Items.WideStrings = (
          'TTL/RS485'
          'CAN'
          '12C'
          'SPI'
          '8Pin Input(HEX)'
          '8Pin Input(BCD)'
          '8Pin Input(Number)')
      end
      object cbJ4: TTntComboBox
        Left = 172
        Top = 144
        Width = 128
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 6
        Items.WideStrings = (
          'Relay Out'
          'CDS'
          'DHT22(ONLY NoLAN)'
          'DS1302'
          'SHT31(DB502)'
          '2Port Input'
          'RelayOut & NoRTC'
          'Relay 4Port')
      end
      object pnCaption: TTntPanel
        Left = 0
        Top = 0
        Width = 317
        Height = 25
        Align = alTop
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clBlack
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = [fsBold]
        ParentFont = False
        TabOrder = 7
        OnMouseDown = pnCaptionMouseDown
        OnMouseMove = pnCaptionMouseMove
        OnMouseUp = pnCaptionMouseUp
        object pnBorderIcon: TTntPanel
          Left = 241
          Top = 0
          Width = 76
          Height = 25
          Align = alRight
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object btnHide: TTntSpeedButton
            Left = 0
            Top = 0
            Width = 25
            Height = 25
            Flat = True
            Glyph.Data = {
              76060000424D7606000000000000360400002800000018000000180000000100
              0800000000004002000000000000000000000001000000000000000000008E8E
              8E00808080008D8C8C007E7D7D008E8E8E006767670067676700000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000002020202020202020202
              0202020202020200000000000000010101010101010101010101010101010100
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000}
            Visible = False
            OnClick = btnHideClick
          end
          object btnMax: TTntSpeedButton
            Left = 25
            Top = 0
            Width = 25
            Height = 25
            Flat = True
            Glyph.Data = {
              76060000424D7606000000000000360400002800000018000000180000000100
              0800000000004002000000000000000000000001000000000000000000008080
              8000696868008D8C8C007E7D7D008E8E8E006767670067676700000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000101
              0101010101010101010101010101000000000000000001010101010101010101
              0101010101010000000000000000010100000000000000000000000001010000
              0000000000000101000000000000000000000000010100000000000000000101
              0000000000000000000000000101000000000000000001010000000000000000
              0000000001010000000000000000010100000000000000000000000001010000
              0000000000000101000000000000000000000000010100000000000000000101
              0000000000000000000000000101000000000000000001010000000000000000
              0000000001010000000000000000010100000000000000000000000001010000
              0000000000000101000000000000000000000000010100000000000000000101
              0000000000000000000000000101000000000000000001010000000000000000
              0000000001010000000000000000010101010101010101010101010101010000
              0000000000000101010101010101010101010101010100000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000}
            Visible = False
            OnClick = btnMaxClick
          end
          object btnExit: TTntSpeedButton
            Left = 50
            Top = 0
            Width = 25
            Height = 25
            Flat = True
            Glyph.Data = {
              76060000424D7606000000000000360400002800000018000000180000000100
              0800000000004002000000000000000000000001000000000000000000008685
              8500696868008D8C8C007E7D7D008E8E8E006767670067676700000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000102
              0000000000000000000000020100000000000000000603030200000000000000
              0000020303060000000000000000020303020000000000000002030302000000
              0000000000000002030302000000000002030302000000000000000000000000
              0203030200000002030302000000000000000000000000000002030302000203
              0302000000000000000000000000000000000203030403030200000000000000
              0000000000000000000000020305030200000000000000000000000000000000
              0000000203050302000000000000000000000000000000000000020303040303
              0200000000000000000000000000000000020303020002030302000000000000
              0000000000000000020303020000000203030200000000000000000000000002
              0303020000000000020303020000000000000000000002030302000000000000
              0002030302000000000000000002030302000000000000000000020303020000
              0000000000000102000000000000000000000002010000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000}
            OnClick = btnExitClick
          end
        end
      end
      object gbSubCom: TTntPanel
        Left = 172
        Top = 64
        Width = 127
        Height = 24
        BevelOuter = bvNone
        TabOrder = 8
        object pnSubCom: TTntPanel
          Left = 2
          Top = 2
          Width = 123
          Height = 20
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object rbWrite: TTntRadioButton
            Left = 6
            Top = 1
            Width = 55
            Height = 18
            Caption = 'Write'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            TabOrder = 0
            OnClick = rbWriteClick
          end
          object rbRead: TTntRadioButton
            Left = 64
            Top = 1
            Width = 54
            Height = 18
            Caption = 'Read'
            Checked = True
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            TabOrder = 1
            TabStop = True
            OnClick = rbWriteClick
          end
        end
      end
      object cbBoardRateTTL: TTntComboBox
        Left = 172
        Top = 196
        Width = 128
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 9
        Items.WideStrings = (
          '9600'
          '19200'
          '38400'
          '57600'
          '115200'
          '230400'
          '460800'
          '921600')
      end
      object cbBoardRate485: TTntComboBox
        Left = 172
        Top = 222
        Width = 128
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 10
        Items.WideStrings = (
          '9600'
          '19200'
          '38400'
          '57600'
          '115200'
          '230400'
          '460800'
          '921600')
      end
      object pnBtnComPort: TTntPanel
        Left = 12
        Top = 277
        Width = 182
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 11
        object BtnComPort2: TTntSpeedButton
          Left = 8
          Top = 0
          Width = 180
          Height = 30
          Caption = 'Communication Setting'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = [fsBold]
          ParentFont = False
          Visible = False
        end
        object BtnComPort: TTeButton
          Left = 0
          Top = 0
          Width = 182
          Height = 30
          OnClick = BtnComPortClick
          Caption = 'Communication Setting'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clNavy
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = [fsBold]
          ParentFont = False
          TabOrder = 0
        end
      end
    end
  end
  object TeButton1: TTeButton
    Left = 439
    Top = 266
    Width = 76
    Height = 34
    OnClick = BitBtnOkClick
    Caption = #54869#51064'(&S)'
    ModalResult = 1
    NumGlyphs = 2
    Glyph.Data = {
      DE010000424DDE01000000000000760000002800000024000000120000000100
      0400000000006801000000000000000000001000000000000000000000000000
      80000080000000808000800000008000800080800000C0C0C000808080000000
      FF0000FF000000FFFF00FF000000FF00FF00FFFF0000FFFFFF00388888888877
      F7F787F8888888888333333F00004444400888FFF444448888888888F333FF8F
      000033334D5007FFF4333388888888883338888F0000333345D50FFFF4333333
      338F888F3338F33F000033334D5D0FFFF43333333388788F3338F33F00003333
      45D50FEFE4333333338F878F3338F33F000033334D5D0FFFF43333333388788F
      3338F33F0000333345D50FEFE4333333338F878F3338F33F000033334D5D0FFF
      F43333333388788F3338F33F0000333345D50FEFE4333333338F878F3338F33F
      000033334D5D0EFEF43333333388788F3338F33F0000333345D50FEFE4333333
      338F878F3338F33F000033334D5D0EFEF43333333388788F3338F33F00003333
      4444444444333333338F8F8FFFF8F33F00003333333333333333333333888888
      8888333F00003333330000003333333333333FFFFFF3333F00003333330AAAA0
      333333333333888888F3333F00003333330000003333333333338FFFF8F3333F
      0000}
    TabOrder = 1
  end
end
