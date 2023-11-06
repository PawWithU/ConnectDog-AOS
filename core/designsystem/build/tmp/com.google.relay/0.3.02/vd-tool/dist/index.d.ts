import execa from 'execa';
/**
 * spawn vd-tool process
 *
 * @param args vd-tool cli args
 * @param options execa options
 */
export declare function vdTool(args?: readonly string[], options?: execa.Options): Promise<execa.ExecaReturnValue<string>>;
declare type VdConvertOptions = {
    outDir?: string;
    width?: number;
    height?: number;
    addHeader?: boolean;
};
declare type VdConvertResult = {
    input: string;
    output: string;
    warnings?: string[];
    errors?: string[];
};
/**
 * SVG to Vector Drawable conversion
 *
 * @param input file or directory path
 * @param options VdConvertOptions
 */
export declare function vdConvert(input: string, options?: VdConvertOptions): Promise<VdConvertResult>;
export {};
//# sourceMappingURL=index.d.ts.map