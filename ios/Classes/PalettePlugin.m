#import "PalettePlugin.h"
#import <palette/palette-Swift.h>

@implementation PalettePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPalettePlugin registerWithRegistrar:registrar];
}
@end
