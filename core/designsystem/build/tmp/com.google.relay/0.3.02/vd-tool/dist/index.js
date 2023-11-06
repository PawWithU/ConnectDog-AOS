"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.vdConvert = exports.vdTool = void 0;
const upath_1 = __importDefault(require("upath"));
const execa_1 = __importDefault(require("execa"));
/**
 * spawn vd-tool process
 *
 * @param args vd-tool cli args
 * @param options execa options
 */
function vdTool(args, options) {
    return __awaiter(this, void 0, void 0, function* () {
        const file = upath_1.default.join(__dirname, '..', 'bin', 'vd-tool');
        if (!options)
            options = { stderr: process.stderr, stdout: process.stdout };
        Object.assign(options, { shell: true });
        return yield execa_1.default(file, args, options);
    });
}
exports.vdTool = vdTool;
/**
 * SVG to Vector Drawable conversion
 *
 * @param input file or directory path
 * @param options VdConvertOptions
 */
function vdConvert(input, options = {}) {
    return __awaiter(this, void 0, void 0, function* () {
        const args = ['-c'];
        const { outDir, width, height, addHeader } = options;
        args.push('-in', String(input));
        if (outDir)
            args.push('-out', String(outDir));
        if (width)
            args.push('-widthDp', String(width));
        if (height)
            args.push('-heightDp', String(height));
        if (addHeader)
            args.push('--addHeader');
        const { stderr } = yield vdTool(args, { stderr: 'pipe', stdout: 'pipe' });
        const { dir, name } = upath_1.default.parse(input);
        const output = upath_1.default.join(outDir || dir, name) + '.xml';
        const result = { input, output };
        const errors = [];
        const warnings = [];
        for (const line of stderr.split(/\r?\n/)) {
            if (line.startsWith('ERROR'))
                errors.push(line);
            else if (line.startsWith('WARNING'))
                warnings.push(line);
        }
        if (errors.length)
            result.errors = errors;
        if (warnings.length)
            result.warnings = warnings;
        return result;
    });
}
exports.vdConvert = vdConvert;
//# sourceMappingURL=index.js.map